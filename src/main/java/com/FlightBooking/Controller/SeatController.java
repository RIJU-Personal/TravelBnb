package com.FlightBooking.Controller;

import com.FlightBooking.Payload.SeatDetailsDTO;
import com.FlightBooking.Service.SeatDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatController {
    @Autowired
    private SeatDetailsService seatDetailsService;

    public SeatController(SeatDetailsService seatDetailsService) {
        this.seatDetailsService = seatDetailsService;
    }

    // Create a new seat detail
    @PostMapping("/create")
    public ResponseEntity<SeatDetailsDTO> createSeat(@RequestBody SeatDetailsDTO seatDetailsDTO) {
        SeatDetailsDTO createdSeat = seatDetailsService.createSeat(seatDetailsDTO);
        return new ResponseEntity<>(createdSeat, HttpStatus.CREATED);
    }

    // Get a seat detail by ID
    @GetMapping("/{seatId}")
    public ResponseEntity<SeatDetailsDTO> getSeatById(@PathVariable Long seatId) {
        SeatDetailsDTO seatDetails = seatDetailsService.getSeatById(seatId);
        return new ResponseEntity<>(seatDetails, HttpStatus.OK);
    }

    // Get all seat details
    @GetMapping
    public ResponseEntity<List<SeatDetailsDTO>> getAllSeats() {
        List<SeatDetailsDTO> seatDetails = seatDetailsService.getAllSeats();
        return new ResponseEntity<>(seatDetails, HttpStatus.OK);
    }

    // Update a seat detail
    @PutMapping("/{seatId}")
    public ResponseEntity<SeatDetailsDTO> updateSeat(
            @PathVariable Long seatId,
            @RequestBody SeatDetailsDTO seatDetailsDTO) {
        SeatDetailsDTO updatedSeat = seatDetailsService.updateSeat(seatId, seatDetailsDTO);
        return new ResponseEntity<>(updatedSeat, HttpStatus.OK);
    }

    // Delete a seat detail
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long seatId) {
        seatDetailsService.deleteSeat(seatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
