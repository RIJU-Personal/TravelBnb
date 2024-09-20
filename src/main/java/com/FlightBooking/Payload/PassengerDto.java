package com.FlightBooking.Payload;

import lombok.Data;

@Data
public class PassengerDto {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String username;
    private String password;
    private String role;
}
