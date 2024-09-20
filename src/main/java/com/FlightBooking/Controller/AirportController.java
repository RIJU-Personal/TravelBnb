package com.FlightBooking.Controller;

import com.FlightBooking.Payload.AirportDTO;
import com.FlightBooking.Service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
public class AirportController {

    private AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    // Create a new airport
    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        AirportDTO createdAirport = airportService.createAirport(airportDTO);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    // Get an airport by ID
    @GetMapping("/{airportId}")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long airportId) {
        AirportDTO airportDTO = airportService.getAirportById(airportId);
        return new ResponseEntity<>(airportDTO, HttpStatus.OK);
    }

    // Get all airports
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    // Update an airport by ID
    @PutMapping("/{airportId}")
    public ResponseEntity<AirportDTO> updateAirport(
            @PathVariable Long airportId,
            @RequestBody AirportDTO airportDTO) {
        AirportDTO updatedAirport = airportService.updateAirport(airportId, airportDTO);
        return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
    }

    // Delete an airport by ID
    @DeleteMapping("/{airportId}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long airportId) {
        airportService.deleteAirport(airportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
