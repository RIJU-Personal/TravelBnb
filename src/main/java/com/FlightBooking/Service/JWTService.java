package com.FlightBooking.Service;



import com.FlightBooking.Entity.Passenger;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.alogrithm.key}")
    private String alogirthmkey;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    private final String USER_NAME="username";

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(alogirthmkey);
    }
    public String generateToken(Passenger passenger){
        return JWT.create().
                withClaim(USER_NAME,passenger.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).
                withIssuer(issuer).sign(algorithm);
    }
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }

}

