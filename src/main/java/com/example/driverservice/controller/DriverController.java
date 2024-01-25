package com.example.driverservice.controller;

import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDTO;
import com.example.driverservice.dto.LoginDTO;
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
    public ResponseEntity<DriverDTO> registration(@RequestBody @Valid Driver driver) throws InvalidLoginException {
        return driverService.register(driver);
    }

    @GetMapping("")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() throws UserNotFoundException {
        return driverService.getAllDrivers();
    }

    @PostMapping("login")
    public ResponseEntity<DriverDTO> getDriver(@RequestBody @Valid LoginDTO loginDTO) throws InvalidLoginException {
        return driverService.getDriver(loginDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@RequestBody @Valid Driver driver, @PathVariable int id) {
        return driverService.addOrUpdateDriver(driver, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverDTO> deleteDriver(@PathVariable int id) throws UserNotFoundException {
        return driverService.deleteDriver(id);
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<DriverDTO> changeStatus(@PathVariable int id) throws UserNotFoundException {
        return driverService.changeStatus(id);
    }

    @GetMapping("{id}/bank")
    public ResponseEntity<BankDataDto> getBankData(@PathVariable int id) {
        return driverService.getBankData();
    }


//    @GetMapping("available")
//    public ResponseEntity<DriverDTO> findAvailableDriver() throws UserNotFoundException {
//        return driverService.findAvailableDriver();
//    }
}
