package com.FlightBooking.Controller;

import com.FlightBooking.Entity.Flight;
import com.FlightBooking.Payload.FlightDto;
import com.FlightBooking.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto) {
        FlightDto flight = flightService.createFlight(flightDto);
        return new ResponseEntity<>(flight,HttpStatus.CREATED);

    }

    @GetMapping("/flightList")
    public ResponseEntity< List<FlightDto>> getAllFlights() {
        List<FlightDto> allFlights = flightService.getAllFlights();
        return new ResponseEntity<>(allFlights,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightDto flightDto) {
        Flight updatedFlight = flightService.updateFlight(id, flightDto);
        return new ResponseEntity<>(updatedFlight,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }
}
