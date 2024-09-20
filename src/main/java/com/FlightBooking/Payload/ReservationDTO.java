package com.FlightBooking.Payload;


import com.FlightBooking.Entity.Passenger;
import com.FlightBooking.Entity.SeatDetails;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long reservationId;
    private Long passengerId;
    private Long seatId;
    private Double seatCost; // This will be calculated based on seat class
    private LocalDate dateOfReservation;
}
