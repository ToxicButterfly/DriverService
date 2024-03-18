package com.example.driverservice.controller;

import com.example.driverservice.dto.*;
import com.example.driverservice.dto.request.DriverCreateRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drivers")
public class DriverController {


    private final DriverService driverService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<DriverDto> registration(@RequestBody @Valid DriverCreateRequest driver) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.register(driver));
    }

    @GetMapping
    public ResponseEntity<DriversDto> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable int id) {
        return ResponseEntity.ok(driverService.getDriver(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<DriverDto> updateDriver(@RequestBody @Valid Driver driver, @PathVariable int id) {
        return ResponseEntity.ok(driverService.addOrUpdateDriver(driver, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<DriverDto> deleteDriver(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(driverService.deleteDriver(id));
    }

    @PatchMapping("status/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<DriverDto> changeStatus(@PathVariable int id) {
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
