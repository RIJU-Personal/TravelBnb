package com.FlightBooking.Config;

import com.FlightBooking.Entity.Passenger;
import com.FlightBooking.Repository.PassengerRepository;
import com.FlightBooking.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private PassengerRepository passengerRepository;

    public JWTRequestFilter(JWTService jwtService, PassengerRepository passengerRepository) {
        this.jwtService = jwtService;
        this.passengerRepository = passengerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String username = jwtService.getUsername(token);
            Optional<Passenger> byUsername = passengerRepository.findByUsername(username);
            if(byUsername.isPresent()) {
                Passenger passenger = byUsername.get();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(passenger, null, Collections.singleton(new SimpleGrantedAuthority(passenger.getRole())));
                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }
        filterChain.doFilter(request, response);
    }
}
