package com.FlightBooking.Controller;


import com.FlightBooking.Payload.JWTTokenDto;
import com.FlightBooking.Payload.LoginDto;
import com.FlightBooking.Payload.PassengerDto;
import com.FlightBooking.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/addPassenger")
    public ResponseEntity<PassengerDto>AddPassenger(@RequestBody PassengerDto passengerDto){
        PassengerDto addPassenger = passengerService.AddPassenger(passengerDto);
        return new ResponseEntity<>(addPassenger, HttpStatus.CREATED);
    }
    // Get Passenger by ID
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassengerById(@PathVariable Long id) {
        PassengerDto passengerDto = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passengerDto);
    }

    // Get All Passengers
    @GetMapping("/passengerList")
    public ResponseEntity<List<PassengerDto>> getAllPassengers() {
        List<PassengerDto> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    // Delete Passenger by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?>verifyLogin(@RequestBody LoginDto loginDto){
        System.out.println(loginDto.getUsername());
        System.out.println(loginDto.getPassword());
        String token = passengerService.verifyPassenger(loginDto);

        if(token!=null){
            JWTTokenDto jwtTokenDto=new JWTTokenDto();
            jwtTokenDto.setType("jwt token");
            jwtTokenDto.setToken(token);
            return new ResponseEntity<>(jwtTokenDto,HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid credential",HttpStatus.BAD_REQUEST);
    }

}

