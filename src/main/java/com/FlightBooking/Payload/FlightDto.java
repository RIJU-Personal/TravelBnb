package com.FlightBooking.Payload;



import lombok.Data;

@Data
public class FlightDto {
    private String flightNumber;
    private String airline;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
}
