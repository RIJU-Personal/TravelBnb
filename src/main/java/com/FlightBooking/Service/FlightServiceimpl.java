package com.FlightBooking.Service;

import com.FlightBooking.Entity.Flight;
import com.FlightBooking.Payload.FlightDto;
import com.FlightBooking.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FlightServiceimpl implements FlightService{
    @Autowired
    private FlightRepository flightRepository;

    public FlightServiceimpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = convertToEntity(flightDto);
        Flight savedFlight = flightRepository.save(flight);
        return convertToDto(savedFlight);
    }

    @Override
    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));
    }

//    @Override
//    public FlightDto getFlightById(Long id) {
//        FlightDto flight = getFlightById(id);
//        return convertToDto(flight);
//    }

    @Override
    public Flight updateFlight(Long id, FlightDto flightDto) {
        Flight flight = getFlightById(id);
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setAirline(flightDto.getAirline());
        flight.setDepartureAirport(flightDto.getDepartureAirport());
        flight.setArrivalAirport(flightDto.getArrivalAirport());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        return flightRepository.save(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    private FlightDto convertToDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setAirline(flight.getAirline());
        flightDto.setDepartureAirport(flight.getDepartureAirport());
        flightDto.setArrivalAirport(flight.getArrivalAirport());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setArrivalTime(flight.getArrivalTime());
        return flightDto;
    }
    private Flight convertToEntity(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setAirline(flightDto.getAirline());
        flight.setDepartureAirport(flightDto.getDepartureAirport());
        flight.setArrivalAirport(flightDto.getArrivalAirport());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        return flight;
    }


}
