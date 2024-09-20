package com.FlightBooking.Repository;

import com.FlightBooking.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByUsername(String username);
}