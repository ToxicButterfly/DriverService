package com.example.driverservice.service.impl;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.RideRequest;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repo.DriverRepo;
import com.example.driverservice.service.DriverService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.driverservice.util.Messages.*;

@Service
@Slf4j
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final DriverDtoConverter driverDtoConverter;
    private final DriverProducer driverProducer;

    @SneakyThrows
    public DriverDto register(Driver driver) {
        if (driverRepo.findByEmailOrUsername(driver.getEmail(), driver.getUsername()).isPresent()) {
            throw new InvalidLoginException(INVALID_LOGIN_MESSAGE);
        }
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        driverRepo.save(driver);
        return driverDtoConverter.convertDriverToDriverDto(driver);
    }

    public DriversDto getAllDrivers() {
        return new DriversDto(driverRepo.findAll()
                .stream()
                .map(driverDtoConverter::convertDriverToDriverDto)
                .toList());
    }

    @SneakyThrows
    public DriverDto getDriver(LoginDto loginDto) {
        Driver driver = driverRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new InvalidLoginException(INVALID_LOGIN_MESSAGE));
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

    @SneakyThrows
    public DriverDto deleteDriver(int id) {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(DRIVER_NOT_FOUND_MESSAGE));
        driverRepo.deleteById(id);
        return driverDtoConverter.convertDriverToDriverDto(driver);
    }

    @SneakyThrows
    public DriverDto changeStatus(int id) {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(DRIVER_NOT_FOUND_MESSAGE));
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
                .cvv(CVV)
                .cardNumber(CARD_NUMBER)
                .expirationDate(EXPIRATION_DATE)
                .balance(BALANCE)
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