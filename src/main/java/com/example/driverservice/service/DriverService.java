package com.example.driverservice.service;

import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.model.Driver;

public interface DriverService {
    DriverDto register(Driver driver) throws InvalidLoginException;
    DriversDto getAllDrivers();
    DriverDto getDriver(LoginDto loginDto) throws InvalidLoginException;
    DriverDto addOrUpdateDriver(Driver driver, int id);
    DriverDto deleteDriver(int id) throws UserNotFoundException;
    DriverDto changeStatus(int id) throws UserNotFoundException;
    void findAvailableDriver(Integer id);
    BankDataDto getBankData();
    void updateRating(UpdateRatingRequest request, Integer id);
    RatingResponse askOpinion(int id);
}
