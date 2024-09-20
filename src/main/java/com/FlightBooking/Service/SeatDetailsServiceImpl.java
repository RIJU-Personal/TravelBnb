package com.FlightBooking.Service;

import com.FlightBooking.Entity.Flight;
import com.FlightBooking.Entity.SeatDetails;
import com.FlightBooking.Payload.SeatDetailsDTO;
import com.FlightBooking.Repository.FlightRepository;
import com.FlightBooking.Repository.SeatDetailsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class SeatDetailsServiceImpl implements SeatDetailsService{
    private SeatDetailsRepository seatDetailsRepository;
    private FlightRepository flightRepository;

    public SeatDetailsServiceImpl(SeatDetailsRepository seatDetailsRepository, FlightRepository flightRepository) {
        this.seatDetailsRepository = seatDetailsRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public SeatDetailsDTO createSeat(SeatDetailsDTO seatDetailsDTO) {

        // Fetch the Flight entity from the repository
        Flight flight = flightRepository.findById(seatDetailsDTO.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + seatDetailsDTO.getFlightId()));

        // Create and set the SeatDetails entity
        SeatDetails seatDetails = new SeatDetails();
        seatDetails.setSeatClass(seatDetailsDTO.getSeatClass());
        seatDetails.setFlight(flight);

        // Calculate seat cost before saving
        double calculatedCost = calculateSeatCost(seatDetails);
        seatDetails.setSeatCost(calculatedCost);

        SeatDetails savedSeatDetails = seatDetailsRepository.save(seatDetails);
        return mapToDTO(savedSeatDetails);
    }

    @Override
    public SeatDetailsDTO getSeatById(Long seatId) {
        SeatDetails seatDetails = seatDetailsRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));
        return mapToDTO(seatDetails);
    }

    @Override
    public List<SeatDetailsDTO> getAllSeats() {
        List<SeatDetails> seatDetailsList = seatDetailsRepository.findAll();
        return seatDetailsList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDetailsDTO updateSeat(Long seatId, SeatDetailsDTO seatDetailsDTO) {
        SeatDetails seatDetails = seatDetailsRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));

        // Update the seat class
        seatDetails.setSeatClass(seatDetailsDTO.getSeatClass());

        // Fetch the Flight entity using the flightId from the DTO
        Flight flight = flightRepository.findById(seatDetailsDTO.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + seatDetailsDTO.getFlightId()));

        // Update the flight on the SeatDetails entity
        seatDetails.setFlight(flight);

        // Recalculate seat cost when updating
        double recalculatedCost = calculateSeatCost(seatDetails);
        seatDetails.setSeatCost(recalculatedCost);

        // Save the updated SeatDetails entity
        SeatDetails updatedSeatDetails = seatDetailsRepository.save(seatDetails);

        // Return the updated DTO
        return mapToDTO(updatedSeatDetails);
    }

    @Override
    public void deleteSeat(Long seatId) {
        SeatDetails seatDetails = seatDetailsRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));
        seatDetailsRepository.delete(seatDetails);
    }
    // Helper method to convert entity to DTO
    private SeatDetailsDTO mapToDTO(SeatDetails seatDetails) {
        SeatDetailsDTO seatDetailsDTO = new SeatDetailsDTO();
        seatDetailsDTO.setSeatId(seatDetails.getSeatId());
        seatDetailsDTO.setSeatClass(seatDetails.getSeatClass());
        seatDetailsDTO.setSeatCost(seatDetails.getSeatCost());
        if (seatDetails.getFlight() != null) {
            seatDetailsDTO.setFlightId(seatDetails.getFlight().getId()); // Set flightId from Flight entity
        }
        return seatDetailsDTO;
    }

    // Helper method to convert DTO to entity
    private SeatDetails mapToEntity(SeatDetailsDTO seatDetailsDTO) {
        SeatDetails seatDetails = new SeatDetails();
        seatDetails.setSeatClass(seatDetailsDTO.getSeatClass());
        seatDetails.setSeatId(seatDetailsDTO.getSeatId());
    //    seatDetails.setFlight(seatDetailsDTO.getFlightId());
        seatDetails.setSeatCost(seatDetailsDTO.getSeatCost());
        return seatDetails;
        // Map other fields as necessary

    }
    /**
     * Method to calculate seat cost based on different factors
     */

    private double calculateSeatCost(SeatDetails seatDetails) {
        double basePrice = 100.0;
        double classMultiplier = 1.0;

        if ("Premium Economy".equalsIgnoreCase(seatDetails.getSeatClass())) {
            classMultiplier = 1.5;
        } else if ("Business".equalsIgnoreCase(seatDetails.getSeatClass())) {
            classMultiplier = 2.0;
        }

        // Ensure flight is not null before accessing its properties
        if (seatDetails.getFlight() != null) {
            // Extract departure time from Flight entity
            String departureTimeStr = seatDetails.getFlight().getDepartureTime(); // Assuming this is a String
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);

            long daysUntilFlight = ChronoUnit.DAYS.between(LocalDateTime.now(), departureTime);

            double timeMultiplier = 1.0;
            if (daysUntilFlight <= 7) {
                timeMultiplier = 1.5;
            }

            return basePrice * classMultiplier * timeMultiplier;
        } else {
            throw new RuntimeException("Flight information is missing");
        }
    }

}

