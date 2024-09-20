package com.FlightBooking.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SeatDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;
    private String seatClass; // Economy, Premium Economy, Business
    private Double seatCost;
}
