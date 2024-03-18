package com.example.driverservice.service;

import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.DriverCreateRequest;
import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.model.Driver;

public interface DriverService {
    DriverDto register(DriverCreateRequest request);
    DriversDto getAllDrivers();
    DriverDto getDriver(int id);
    DriverDto addOrUpdateDriver(Driver driver, int id);
    DriverDto deleteDriver(int id);
    DriverDto changeStatus(int id);
    void findAvailableDriver(DriverRequest request);
    BankDataDto getBankData();
    void updateRating(UpdateRatingRequest request, Integer id);
    RatingResponse askOpinion(int id);
}
