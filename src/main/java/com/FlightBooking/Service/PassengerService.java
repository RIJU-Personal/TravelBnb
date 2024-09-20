package com.FlightBooking.Service;

import com.FlightBooking.Payload.LoginDto;
import com.FlightBooking.Payload.PassengerDto;

import java.util.List;

public interface PassengerService {
     PassengerDto AddPassenger(PassengerDto passengerDto);

    PassengerDto getPassengerById(Long id);

    List<PassengerDto> getAllPassengers();

    void deletePassenger(Long id);

   String verifyPassenger(LoginDto loginDto);
}
