package com.FlightBooking.Controller;

import com.FlightBooking.Entity.Passenger;
import com.FlightBooking.Entity.Reservation;
import com.FlightBooking.Payload.ReservationDTO;
import com.FlightBooking.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@AuthenticationPrincipal Passenger passenger, @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    // Get a reservation by ID
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId) {
        ReservationDTO reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
    // Get all reservations
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Update a reservation
    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    // Delete a reservation
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

