package com.FlightBooking.Service;

import com.FlightBooking.Payload.AirportDTO;

import java.util.List;

public interface AirportService {
    AirportDTO createAirport(AirportDTO airportDTO);
    AirportDTO getAirportById(Long airportId);
    List<AirportDTO> getAllAirports();
    AirportDTO updateAirport(Long airportId, AirportDTO airportDTO);
    void deleteAirport(Long airportId);
}
