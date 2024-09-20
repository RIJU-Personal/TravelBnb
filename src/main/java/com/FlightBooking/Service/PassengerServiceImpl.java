package com.FlightBooking.Service;

import com.FlightBooking.Entity.Passenger;
import com.FlightBooking.Exception.ResourceNotFoundException;
import com.FlightBooking.Payload.LoginDto;
import com.FlightBooking.Payload.PassengerDto;
import com.FlightBooking.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;
@Service
public class PassengerServiceImpl implements PassengerService{
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private JWTService jwtService;

    public PassengerServiceImpl(PassengerRepository passengerRepository, JWTService jwtService) {
        this.passengerRepository = passengerRepository;

        this.jwtService = jwtService;
    }

    @Override
    public PassengerDto AddPassenger(PassengerDto passengerDto) {
        Passenger passenger=toEntity(passengerDto);
        String hashpw = BCrypt.hashpw(passenger.getPassword(), BCrypt.gensalt(10));
        passenger.setPassword(hashpw);
        Passenger save = passengerRepository.save(passenger);
        return toDto(save);
    }
    // Update an Existing Passenger
    public PassengerDto updatePassenger(Long id, PassengerDto passengerDto) {
        Optional<Passenger> byId = passengerRepository.findById(id);
        Passenger passenger = byId.get();
        passenger.setFirstName(passengerDto.getFirstName());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setEmail(passengerDto.getEmail());
        passenger.setPhoneNumber(passengerDto.getPhoneNumber());
        passenger.setAddress(passengerDto.getAddress());
        passenger.setCity(passengerDto.getCity());
        passenger.setState(passengerDto.getState());
        passenger.setZipcode(passengerDto.getZipcode());
        passenger.setCountry(passengerDto.getCountry());
        passenger.setUsername(passengerDto.getUsername());
        passenger.setPassword(passenger.getPassword());
        Passenger save = passengerRepository.save(passenger);
        return toDto(save);

    }
    // Delete a Passenger by ID
    public void deletePassenger(Long id) {
        passengerRepository.findById(id)
                .ifPresent(passengerRepository::delete);
    }

    @Override
    public String verifyPassenger(LoginDto loginDto) {
        Optional<Passenger> byUsername = passengerRepository.findByUsername(loginDto.getUsername());
        if (byUsername.isPresent()) {
            Passenger passenger = byUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(), passenger.getPassword())) {
                String token = jwtService.generateToken(passenger);
                return token;
            }

        }
        return null;
    }


        // Get a Passenger by ID
        public PassengerDto getPassengerById (Long id){
            Passenger passenger = passengerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
            return toDto(passenger);
        }

        // Get all Passengers
        public List<PassengerDto> getAllPassengers () {
            return passengerRepository.findAll()
                    .stream()
                    .map(PassengerServiceImpl::toDto)
                    .toList();
        }


        public static Passenger toEntity (PassengerDto dto){
            Passenger passenger = new Passenger();
            passenger.setPassengerId(dto.getPassengerId());
            passenger.setFirstName(dto.getFirstName());
            passenger.setLastName(dto.getLastName());
            passenger.setEmail(dto.getEmail());
            passenger.setPhoneNumber(dto.getPhoneNumber());
            passenger.setAddress(dto.getAddress());
            passenger.setCity(dto.getCity());
            passenger.setState(dto.getState());
            passenger.setZipcode(dto.getZipcode());
            passenger.setCountry(dto.getCountry());
            passenger.setUsername(dto.getUsername());
            passenger.setPassword(dto.getPassword());
            passenger.setRole(dto.getRole());
            return passenger;
        }
        public static PassengerDto toDto (Passenger passenger){
            PassengerDto pass = new PassengerDto();
            pass.setPassengerId(passenger.getPassengerId());
            pass.setFirstName(passenger.getFirstName());
            pass.setLastName(passenger.getLastName());
            pass.setEmail(passenger.getEmail());
            pass.setPhoneNumber(passenger.getPhoneNumber());
            pass.setAddress(passenger.getAddress());
            pass.setCity(passenger.getCity());
            pass.setState(passenger.getState());
            pass.setZipcode(passenger.getZipcode());
            pass.setCountry(passenger.getCountry());
            pass.setUsername(passenger.getUsername());
            pass.setPassword(passenger.getPassword());
            pass.setRole(passenger.getRole());
            return pass;
        }
    }


