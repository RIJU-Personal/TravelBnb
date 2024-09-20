package com.FlightBooking.Payload;

import lombok.Data;

@Data
public class AirportDTO {
    private Long airportId;
    private String airportName;
    private String airportCity;
    private String airportCountry;
}
