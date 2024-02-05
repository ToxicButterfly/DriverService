package com.example.driverservice.convert;

import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.model.Driver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public DriverDto convertDriverToDriverDto(Driver driver) {
        return modelMapper.map(driver, DriverDto.class);
    }

    public Driver convertDriverDtoToDriver(DriverDto driverDto) {
        return modelMapper.map(driverDto, Driver.class);
    }

    public LoginDto convertDriverToLoginDto(Driver driver) {
        return modelMapper.map(driver, LoginDto.class);
    }

    public Driver convertLoginDtoToDriver(LoginDto loginDto) {
        return modelMapper.map(loginDto, Driver.class);
    }

}