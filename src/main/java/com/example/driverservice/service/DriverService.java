package com.example.driverservice.service;

import com.example.driverservice.convert.DriverDTOConverter;
import com.example.driverservice.dao.DriverDAO;
import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDTO;
import com.example.driverservice.dto.LoginDTO;
import com.example.driverservice.dto.RatingResponse;
import com.example.driverservice.dto.request.RideRequest;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DriverService {

    private final DriverDAO driverDAO;
    private final DriverDTOConverter driverDTOConverter;
    private final DriverProducer driverProducer;

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

    public void findAvailableDriver(Integer id) {
        List<Driver> drivers = driverDAO.findAllByAvailability(true);
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

    public ResponseEntity<BankDataDto> getBankData() {
        BankDataDto data = BankDataDto.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expirationDate("12/26")
                .balance(90000F)
                .build();
        log.info("Sending bank data");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public void updateRating(UpdateRatingRequest request, Integer id) {
        Driver driver = driverDAO.findById(id).get();
        driver.setRating(request.getRating());
        driverDAO.save(driver);
    }

    public ResponseEntity<RatingResponse> askOpinion(int id) {
        Random random = new Random();
        int r = 1 + random.nextInt(5);
        log.info("Passenger rated {} points", r);
        return new ResponseEntity<>(new RatingResponse(r), HttpStatus.OK);
    }
}