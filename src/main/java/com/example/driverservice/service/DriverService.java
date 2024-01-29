package com.example.driverservice.service;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.repo.DriverRepo;
import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.dto.RatingResponse;
import com.example.driverservice.dto.request.RideRequest;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DriverService {

    private final DriverRepo driverRepo;
    private final DriverDtoConverter driverDtoConverter;
    private final DriverProducer driverProducer;

    public ResponseEntity<DriverDto> register(Driver driver) throws InvalidLoginException {
        Optional<Driver> someone = driverRepo.findByEmailOrUsername(driver.getEmail(), driver.getUsername());
        if (someone.isPresent()) {
            throw new InvalidLoginException("Username or Email is already taken");
        }
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        driverRepo.save(driver);
        return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver), HttpStatus.CREATED);
    }

    public ResponseEntity<List<DriverDto>> getAllDrivers() throws UserNotFoundException {
        List<Driver> drivers = driverRepo.findAll();
        if (drivers.isEmpty())
            throw new UserNotFoundException("There's no drivers");
        List<DriverDto> dtoDrivers = new ArrayList<>();
        for(Driver driver: drivers) {
            dtoDrivers.add(driverDtoConverter.convertDriverToDriverDto(driver));
        }
        return new ResponseEntity<>(dtoDrivers, HttpStatus.OK);
    }
    public ResponseEntity<DriverDto> getDriver(LoginDto loginDto) throws InvalidLoginException {
        Driver driver = driverRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).orElse(new Driver());
        if(driver.getId()!=null) {
            return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver), HttpStatus.FOUND);
        }
        else throw new InvalidLoginException("Invalid email or password");
    }

    public ResponseEntity<DriverDto> addOrUpdateDriver(Driver driver, int id) {
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        if (driverRepo.findById(id).isPresent()) {
            driver.setId(id);
            driverRepo.save(driver);
            return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver), HttpStatus.OK);
        } else {
            driverRepo.save(driver);
            return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<DriverDto> deleteDriver(int id) throws UserNotFoundException {
        Optional<Driver> driver = driverRepo.findById(id);
        if (driver.isPresent()) {
            driverRepo.deleteById(id);
            return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver.get()), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("There's no such driver");
        }
    }

    public ResponseEntity<DriverDto> changeStatus(int id) throws UserNotFoundException {
        Optional<Driver> driver = driverRepo.findById(id);
        if(driver.isPresent()) {
            Driver driver1 = driver.get();
            driver1.setAvailability(!driver1.isAvailability());
            driverRepo.save(driver1);
            return new ResponseEntity<>(driverDtoConverter.convertDriverToDriverDto(driver1), HttpStatus.OK);
        }
        else throw new UserNotFoundException("There's no such driver");
    }

    public void findAvailableDriver(Integer id) {
        List<Driver> drivers = driverRepo.findAllByAvailability(true);
        if (!drivers.isEmpty()) {
            Random random = new Random();
            int r = random.nextInt(drivers.size());
            RideRequest request = RideRequest.builder()
                    .rideId(id)
                    .driverId(drivers.get(r).getId())
                    .driverRating(drivers.get(r).getRating())
                    .build();
            driverProducer.sendMessage(request);
        }
    }

    public ResponseEntity<BankDataDto> getBankData() {
        BankDataDto data = BankDataDto.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expirationDate("12/26")
                .balance(90000F)
                .build();
        log.info("Sending bank data");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public void updateRating(UpdateRatingRequest request, Integer id) {
        Driver driver = driverRepo.findById(id).get();
        driver.setRating(request.getRating());
        driverRepo.save(driver);
    }

    public ResponseEntity<RatingResponse> askOpinion(int id) {
        Random random = new Random();
        int r = 1 + random.nextInt(5);
        log.info("Passenger rated {} points", r);
        return new ResponseEntity<>(new RatingResponse(r), HttpStatus.OK);
    }
}