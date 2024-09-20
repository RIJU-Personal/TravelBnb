package com.FlightBooking.Service;

import com.FlightBooking.Payload.ReservationDTO;

import java.util.List;

public interface ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO);

    ReservationDTO getReservationById(Long reservationId);

    List<ReservationDTO> getAllReservations();

    ReservationDTO updateReservation(Long reservationId, ReservationDTO reservationDTO);

    void deleteReservation(Long reservationId);
}
