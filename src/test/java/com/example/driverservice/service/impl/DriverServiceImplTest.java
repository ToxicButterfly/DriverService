package com.example.driverservice.service.impl;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.DriversDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.dto.request.RideRequest;
import com.example.driverservice.exception.InvalidLoginException;
import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repo.DriverRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.driverservice.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {

    @Mock
    private DriverRepo driverRepo;
    @Mock
    private DriverDtoConverter driverDtoConverter;
    @Mock
    private DriverProducer driverProducer;
    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void registerWhenCredentialsValid() {
        Driver driver = getDefaultDriverToSave();
        Driver savedDriver = getDefaultDriver();
        doReturn(Optional.empty())
                .when(driverRepo)
                .findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);
        doReturn(savedDriver)
                .when(driverRepo)
                .save(driver);

        driverService.register(driver);

        verify(driverRepo).findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);
        verify(driverRepo).save(any(Driver.class));
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

    }

    @Test
    void registerWhenCredentialsNotValid(){
        Driver driver = getDefaultDriverToSave();
        doReturn(Optional.of(driver))
                .when(driverRepo)
                .findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);
        assertThrows(InvalidLoginException.class, ()->driverService.register(driver));
        verify(driverRepo).findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);
    }

    @Test
    void getAllDriversWhenDriversExist() {
        Driver driver = getDefaultDriver();
        List<Driver> driverList = new ArrayList<>(Arrays.asList(driver, driver));
        DriverDto driverDto = getDefaultDriverDto();
        DriversDto driversDto = new DriversDto(Arrays.asList(driverDto, driverDto));
        doReturn(driverList).when(driverRepo).findAll();
        doReturn(driverDto).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        DriversDto response = driverService.getAllDrivers();

        assertEquals(driversDto, response);
        verify(driverRepo).findAll();
        verify(driverDtoConverter, times(2)).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void getAllDriversWhenDriversNotExist() {
        List<Driver> driverList = new ArrayList<>();
        DriversDto driversDto = new DriversDto(List.of());
        doReturn(driverList).when(driverRepo).findAll();

        DriversDto response = driverService.getAllDrivers();

        assertEquals(driversDto, response);
        verify(driverRepo).findAll();
    }

    @Test
    void loginWhenCorrectCredentials() {
        LoginDto login = getDefaultLogin();
        DriverDto driverDto = getDefaultDriverDto();
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findByEmailAndPassword(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        doReturn(driverDto).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        DriverDto response = driverService.getDriver(login);

        assertEquals(driverDto, response);
        verify(driverRepo).findByEmailAndPassword(DEFAULT_EMAIL, DEFAULT_PASSWORD);
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void loginWhenIncorrectCredentials() {
        assertThrows(InvalidLoginException.class, () -> driverService.getDriver(getDefaultLogin()));
    }

    @Test
    void addOrUpdateDriverWhenUpdate() {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        driverService.addOrUpdateDriver(getDefaultDriverToSave(), DEFAULT_ID);

        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).save(any(Driver.class));
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void addOrUpdateDriverWhenAdd() {
        doReturn(Optional.empty()).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        driverService.addOrUpdateDriver(getDefaultDriverToSave(), DEFAULT_ID);

        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).save(any(Driver.class));
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void deleteDriverWhenNoDriver() {
        doReturn(Optional.empty()).when(driverRepo).findById(DEFAULT_ID);
        assertThrows(UserNotFoundException.class, () -> driverService.deleteDriver(DEFAULT_ID));
        verify(driverRepo).findById(DEFAULT_ID);
    }

    @Test
    void deleteDriverWhenDriverExist() {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        DriverDto response = driverService.deleteDriver(DEFAULT_ID);

        assertEquals(getDefaultDriverDto(), response);
        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).deleteById(DEFAULT_ID);
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void changeStatusWhenNoDriver() {
        doReturn(Optional.empty()).when(driverRepo).findById(DEFAULT_ID);
        assertThrows(UserNotFoundException.class, ()-> driverService.changeStatus(DEFAULT_ID));
        verify(driverRepo).findById(DEFAULT_ID);
    }

    @Test
    void changeStatusWhenAvailable() {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getUnavailableDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getUnavailableDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        DriverDto response = driverService.changeStatus(DEFAULT_ID);

        assertEquals(getUnavailableDriverDto(),response);
        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).save(any(Driver.class));
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void changeStatusWhenUnavailable() {
        doReturn(Optional.of(getUnavailableDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        DriverDto response = driverService.changeStatus(DEFAULT_ID);

        assertEquals(getDefaultDriverDto(),response);
        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).save(any(Driver.class));
        verify(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @Test
    void findAvailableDriver() {
        doReturn(Arrays.asList(getDefaultDriver(), getDefaultDriver())).when(driverRepo).findAllByAvailability(true);

        driverService.findAvailableDriver(DEFAULT_ID);

        verify(driverRepo).findAllByAvailability(true);
        verify(driverProducer).sendMessage(any(RideRequest.class));
    }

    @Test
    void getBankData() {
        BankDataDto dataDto = gedDefaultBankDataDto();

        BankDataDto response = driverService.getBankData();

        assertEquals(dataDto, response);
    }

    @Test
    void updateRating() {

        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));

        driverService.updateRating(getDefaultUpdateRatingRequest(), DEFAULT_ID);

        verify(driverRepo).findById(DEFAULT_ID);
        verify(driverRepo).save(any(Driver.class));
    }
}
