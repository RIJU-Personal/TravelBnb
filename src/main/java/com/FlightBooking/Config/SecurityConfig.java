package com.FlightBooking.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {


    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();
//        http.authorizeHttpRequests().requestMatchers("/api/v1/users/login","/api/v1/users/createUser")
//                .permitAll()
//                .requestMatchers("/api/v1/countries/add").hasRole("ADMIN").
//                 requestMatchers("/api/v1/photos/addphoto").hasAnyRole("ADMIN","USER").anyRequest().authenticated();

//        http.authorizeHttpRequests()
//                .requestMatchers("/api/v1/passenger/login", "/api/v1/passenger/addPassenger")
//                .permitAll()  // Allow access to login and signup endpoints without authentication
//                .requestMatchers("/api/v1/passenger/id").hasRole("ADMIN")  // Require ADMIN role for all other passenger-related endpoints
//                .anyRequest().authenticated();  // Require authentication for any other requests
        return http.build();
    }
}