package com.FlightBooking.Payload;

import com.FlightBooking.Entity.Flight;
import lombok.Data;

@Data
public class SeatDetailsDTO {
    private Long seatId;
    private Long flightId;  // Use this instead of Flight object
    private String seatClass; // Economy, Premium Economy, Business
    private Double seatCost;
   // private Long flightId;

}
