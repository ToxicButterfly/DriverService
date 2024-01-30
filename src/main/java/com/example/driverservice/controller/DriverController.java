package com.example.driverservice.controller;

import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.model.Driver;
import com.example.driverservice.service.DriverService;
import com.example.driverservice.service.impl.DriverServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/driver")
public class DriverController {


    final DriverServiceImpl driverService;

    @PostMapping
    public ResponseEntity<DriverDto> registration(@RequestBody @Valid Driver driver) throws InvalidLoginException {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.register(driver));
    }

    @GetMapping
    public ResponseEntity<DriversDto> getAllDrivers() throws UserNotFoundException {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @PostMapping("login")
    public ResponseEntity<DriverDto> getDriver(@RequestBody @Valid LoginDto loginDTO) throws InvalidLoginException {
        return ResponseEntity.ok(driverService.getDriver(loginDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(@RequestBody @Valid Driver driver, @PathVariable int id) {
        return ResponseEntity.ok(driverService.addOrUpdateDriver(driver, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverDto> deleteDriver(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(driverService.deleteDriver(id));
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<DriverDto> changeStatus(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(driverService.changeStatus(id));
    }

    @GetMapping("{id}/bank")
    public ResponseEntity<BankDataDto> getBankData(@PathVariable int id) {
        return ResponseEntity.ok(driverService.getBankData());
    }

    @GetMapping("{id}/rating")
    public ResponseEntity<RatingResponse> askOpinion(@PathVariable int id) {
        return ResponseEntity.ok(driverService.askOpinion(id));
    }

    @PutMapping("{id}/rating")
    public void updateRating(@RequestBody UpdateRatingRequest request, @PathVariable int id) {
        driverService.updateRating(request, id);
    }
}
