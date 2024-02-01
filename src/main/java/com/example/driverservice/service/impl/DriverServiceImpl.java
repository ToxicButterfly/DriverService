package com.example.driverservice.service.impl;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.dto.*;
import com.example.driverservice.repo.DriverRepo;
import com.example.driverservice.dto.request.RideRequest;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import com.example.driverservice.service.DriverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final DriverDtoConverter driverDtoConverter;
    private final DriverProducer driverProducer;

    public DriverDto register(Driver driver) throws InvalidLoginException {
        if (driverRepo.findByEmailOrUsername(driver.getEmail(), driver.getUsername()).isPresent()) {
            throw new InvalidLoginException("Username or Email is already taken");
        }
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        driverRepo.save(driver);
        return driverDtoConverter.convertDriverToDriverDto(driver);
    }

    public DriversDto getAllDrivers() {
        return new DriversDto(driverRepo.findAll().stream()
                .map(driverDtoConverter::convertDriverToDriverDto)
                .toList());
    }

    public DriverDto getDriver(LoginDto loginDto) throws InvalidLoginException {
        Driver driver = driverRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new InvalidLoginException("Invalid email or password"));
        return driverDtoConverter.convertDriverToDriverDto(driver);

    }

    public DriverDto addOrUpdateDriver(Driver driver, int id) {
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        if (driverRepo.findById(id).isPresent()) {
            driver.setId(id);
        }
        driverRepo.save(driver);
        return driverDtoConverter.convertDriverToDriverDto(driver);
    }

    public DriverDto deleteDriver(int id) throws UserNotFoundException {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There's no such driver"));
        driverRepo.deleteById(id);
        return driverDtoConverter.convertDriverToDriverDto(driver);
    }

    public DriverDto changeStatus(int id) throws UserNotFoundException {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There's no such driver"));
        driver.setAvailability(!driver.isAvailability());
        driverRepo.save(driver);
        return driverDtoConverter.convertDriverToDriverDto(driver);
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

    public BankDataDto getBankData() {
        BankDataDto data = BankDataDto.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expirationDate("12/26")
                .balance(90000F)
                .build();
        log.info("Sending bank data");
        return data;
    }

    public void updateRating(UpdateRatingRequest request, Integer id) {
        Driver driver = driverRepo.findById(id).get();
        driver.setRating(request.getRating());
        driverRepo.save(driver);
    }

    public RatingResponse askOpinion(int id) {
        Random random = new Random();
        int r = 1 + random.nextInt(5);
        log.info("Passenger rated {} points", r);
        return new RatingResponse(r);
    }
}