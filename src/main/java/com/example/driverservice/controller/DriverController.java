package com.example.driverservice.controller;

import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.dto.RatingResponse;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.model.Driver;
import com.example.driverservice.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("register")
    public ResponseEntity<DriverDto> registration(@RequestBody @Valid Driver driver) throws InvalidLoginException {
        return driverService.register(driver);
    }

    @GetMapping("")
    public ResponseEntity<List<DriverDto>> getAllDrivers() throws UserNotFoundException {
        return driverService.getAllDrivers();
    }

    @PostMapping("login")
    public ResponseEntity<DriverDto> getDriver(@RequestBody @Valid LoginDto loginDTO) throws InvalidLoginException {
        return driverService.getDriver(loginDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(@RequestBody @Valid Driver driver, @PathVariable int id) {
        return driverService.addOrUpdateDriver(driver, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverDto> deleteDriver(@PathVariable int id) throws UserNotFoundException {
        return driverService.deleteDriver(id);
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<DriverDto> changeStatus(@PathVariable int id) throws UserNotFoundException {
        return driverService.changeStatus(id);
    }

    @GetMapping("{id}/bank")
    public ResponseEntity<BankDataDto> getBankData(@PathVariable int id) {
        return driverService.getBankData();
    }

    @GetMapping("{id}/rating")
    public ResponseEntity<RatingResponse> askOpinion(@PathVariable int id) {
        return driverService.askOpinion(id);
    }

    @PutMapping("{id}/rating")
    public void updateRating(@RequestBody UpdateRatingRequest request, @PathVariable int id) {
        driverService.updateRating(request, id);
    }
}
