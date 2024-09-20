package com.FlightBooking.Service;

import com.FlightBooking.Entity.Airport;
import com.FlightBooking.Payload.AirportDTO;
import com.FlightBooking.Repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceimpl implements AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceimpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public AirportDTO createAirport(AirportDTO airportDTO) {
        Airport airport = mapToEntity(airportDTO);
        Airport savedAirport = airportRepository.save(airport);
        return mapToDTO(savedAirport);
    }

    @Override
    public AirportDTO getAirportById(Long airportId) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new RuntimeException("Airport not found with ID: " + airportId));
        return mapToDTO(airport);
    }

    @Override
    public List<AirportDTO> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirportDTO updateAirport(Long airportId, AirportDTO airportDTO) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new RuntimeException("Airport not found with ID: " + airportId));

        airport.setAirportName(airportDTO.getAirportName());
        airport.setAirportCity(airportDTO.getAirportCity());
        airport.setAirportCountry(airportDTO.getAirportCountry());

        Airport updatedAirport = airportRepository.save(airport);
        return mapToDTO(updatedAirport);
    }

    @Override
    public void deleteAirport(Long airportId) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new RuntimeException("Airport not found with ID: " + airportId));
        airportRepository.delete(airport);
    }

    // Helper method to convert entity to DTO
    private AirportDTO mapToDTO(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setAirportId(airport.getAirportId());
        airportDTO.setAirportName(airport.getAirportName());
        airportDTO.setAirportCity(airport.getAirportCity());
        airportDTO.setAirportCountry(airport.getAirportCountry());
        return airportDTO;
    }

    // Helper method to convert DTO to entity
    private Airport mapToEntity(AirportDTO airportDTO) {
        Airport airport = new Airport();
        airport.setAirportId(airportDTO.getAirportId());
        airport.setAirportName(airportDTO.getAirportName());
        airport.setAirportCity(airportDTO.getAirportCity());
        airport.setAirportCountry(airportDTO.getAirportCountry());
        return airport;
    }
}
