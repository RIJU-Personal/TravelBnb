package com.FlightBooking.Repository;

import com.FlightBooking.Entity.SeatDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatDetailsRepository extends JpaRepository<SeatDetails, Long> {
}