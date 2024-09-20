package com.FlightBooking.Service;

import com.FlightBooking.Payload.SeatDetailsDTO;

import java.util.List;

public interface SeatDetailsService {
    SeatDetailsDTO createSeat(SeatDetailsDTO seatDetailsDTO);

    SeatDetailsDTO getSeatById(Long seatId);

    List<SeatDetailsDTO> getAllSeats();

    SeatDetailsDTO updateSeat(Long seatId, SeatDetailsDTO seatDetailsDTO);

    void deleteSeat(Long seatId);
}
