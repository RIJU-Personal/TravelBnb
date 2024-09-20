package com.FlightBooking.Service;






import com.FlightBooking.Entity.Flight;
import com.FlightBooking.Payload.FlightDto;

import java.util.List;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
    List<FlightDto> getAllFlights();
    Flight getFlightById(Long id);
    Flight updateFlight(Long id, FlightDto flightDto);
    void deleteFlight(Long id);
}