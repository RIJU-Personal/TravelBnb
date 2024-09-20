package com.FlightBooking.Service;

import com.FlightBooking.Entity.Passenger;
import com.FlightBooking.Entity.Reservation;
import com.FlightBooking.Entity.SeatDetails;
import com.FlightBooking.Payload.ReservationDTO;
import com.FlightBooking.Repository.PassengerRepository;
import com.FlightBooking.Repository.ReservationRepository;
import com.FlightBooking.Repository.SeatDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private PassengerRepository passengerRepository;
    private SeatDetailsRepository seatDetailsRepository;
    private ReservationRepository reservationRepository;

    public ReservationServiceImpl(PassengerRepository passengerRepository, SeatDetailsRepository seatDetailsRepository, ReservationRepository reservationRepository) {
        this.passengerRepository = passengerRepository;
        this.seatDetailsRepository = seatDetailsRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {

        Passenger passenger = passengerRepository.findById(reservationDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        SeatDetails seatDetails = seatDetailsRepository.findById(reservationDTO.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // Calculate seat cost
        Double seatCost = seatDetails.getSeatCost();

        Reservation reservation = new Reservation();
        reservation.setPassenger(passenger);
        reservation.setSeatDetails(seatDetails);
        reservation.setDateOfReservation(reservationDTO.getDateOfReservation());
        reservation.setSeatCost(seatCost); // Set the calculated seat cost

        reservation = reservationRepository.save(reservation);
        return convertEntityToDto(reservation);
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return convertEntityToDto(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO updateReservation(Long reservationId, ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Passenger passenger = passengerRepository.findById(reservationDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        SeatDetails seatDetails = seatDetailsRepository.findById(reservationDTO.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // Update the seat cost
        Double seatCost = seatDetails.getSeatCost();

        reservation.setPassenger(passenger);
        reservation.setSeatDetails(seatDetails);
        reservation.setDateOfReservation(reservationDTO.getDateOfReservation());
        reservation.setSeatCost(seatCost); // Update the seat cost

        reservation = reservationRepository.save(reservation);
        return convertEntityToDto(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservationRepository.delete(reservation);
    }


    private ReservationDTO convertEntityToDto(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setPassengerId(reservation.getPassenger().getPassengerId());
        reservationDTO.setSeatId(reservation.getSeatDetails().getSeatId());
        reservationDTO.setSeatCost(reservation.getSeatCost());
        reservationDTO.setDateOfReservation(reservation.getDateOfReservation());
        return reservationDTO;
    }

    private Reservation convertDtoToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationDTO.getReservationId());

        // Fetch related entities from the repositories
        Passenger passenger = passengerRepository.findById(reservationDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        SeatDetails seatDetails = seatDetailsRepository.findById(reservationDTO.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        reservation.setPassenger(passenger);
        reservation.setSeatDetails(seatDetails);
        reservation.setSeatCost(reservationDTO.getSeatCost()); // This should be set based on the seat details
        reservation.setDateOfReservation(reservationDTO.getDateOfReservation());
        return reservation;
    }
}
