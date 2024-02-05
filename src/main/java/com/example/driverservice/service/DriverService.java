package com.example.driverservice.service;

import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.model.Driver;

public interface DriverService {
    DriverDto register(Driver driver);
    DriversDto getAllDrivers();
    DriverDto getDriver(LoginDto loginDto);
    DriverDto addOrUpdateDriver(Driver driver, int id);
    DriverDto deleteDriver(int id);
    DriverDto changeStatus(int id);
    void findAvailableDriver(Integer id);
    BankDataDto getBankData();
    void updateRating(UpdateRatingRequest request, Integer id);
    RatingResponse askOpinion(int id);
}
