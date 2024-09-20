package com.FlightBooking.Payload;

import lombok.Data;

@Data

public class JWTTokenDto {
    private String token;
    private String type;

}
