package com.example.driverservice.service;

import com.example.driverservice.convert.DriverDTOConverter;
import com.example.driverservice.dao.DriverDAO;
import com.example.driverservice.dto.DriverDTO;
import com.example.driverservice.dto.LoginDTO;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverService {

    @Autowired
    DriverDAO driverDAO;
    @Autowired
    DriverDTOConverter driverDTOConverter;

    public ResponseEntity<DriverDTO> register(Driver driver) throws InvalidLoginException {
        Optional<Driver> someone = driverDAO.findByEmailOrUsername(driver.getEmail(), driver.getUsername());
        if(someone.isPresent())
            throw new InvalidLoginException("Username or Email is already taken");
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        driverDAO.save(driver);
        return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver), HttpStatus.CREATED);
    }

    public ResponseEntity<List<DriverDTO>> getAllDrivers() throws UserNotFoundException {
        List<Driver> drivers = driverDAO.findAll();
        if (drivers.isEmpty())
            throw new UserNotFoundException("There's no drivers");
        List<DriverDTO> dtoDrivers = new ArrayList<>();
        for(Driver driver: drivers) {
            dtoDrivers.add(driverDTOConverter.convertDriverToDriverDTO(driver));
        }
        return new ResponseEntity<>(dtoDrivers, HttpStatus.OK);
    }
    public ResponseEntity<DriverDTO> getDriver(LoginDTO loginDTO) throws InvalidLoginException {
        Driver driver = driverDAO.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword()).orElse(new Driver());
        if(driver.getId()!=null) {
            return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver), HttpStatus.FOUND);
        }
        else throw new InvalidLoginException("Invalid email or password");
    }

    public ResponseEntity<DriverDTO> addOrUpdateDriver(Driver driver, int id) {
        driver.setAvailability(true);
        driver.setRating(3.0F);
        Date current = new Date();
        driver.setRegisterDate(current);
        if(driverDAO.findById(id).isPresent()) {
            driver.setId(id);
            driverDAO.save(driver);
            return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver), HttpStatus.OK);
        }
        else {
            driverDAO.save(driver);
            return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<DriverDTO> deleteDriver(int id) throws UserNotFoundException {
        Optional<Driver> driver = driverDAO.findById(id);
        if(driver.isPresent()) {
            driverDAO.deleteById(id);
            return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver.get()), HttpStatus.OK);
        }
        else throw new UserNotFoundException("There's no such driver");
    }

    public ResponseEntity<DriverDTO> changeStatus(int id) throws UserNotFoundException {
        Optional<Driver> driver = driverDAO.findById(id);
        if(driver.isPresent()) {
            Driver driver1 = driver.get();
            driver1.setAvailability(!driver1.isAvailability());
            driverDAO.save(driver1);
            return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(driver1), HttpStatus.OK);
        }
        else throw new UserNotFoundException("There's no such driver");
    }

    public ResponseEntity<DriverDTO> findAvailableDriver() throws UserNotFoundException {
        List<Driver> drivers = driverDAO.findAllByAvailability(true).get();
        if (drivers.isEmpty())
            throw new UserNotFoundException("There's no available driver at the moment");
        Random random = new Random();
        int r = random.nextInt(drivers.size());
        return new ResponseEntity<>(driverDTOConverter.convertDriverToDriverDTO(drivers.get(r)), HttpStatus.OK);
    }
}