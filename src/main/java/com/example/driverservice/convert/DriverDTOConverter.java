package com.example.driverservice.convert;

import com.example.driverservice.dto.DriverDTO;
import com.example.driverservice.dto.LoginDTO;
import com.example.driverservice.model.Driver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public DriverDTO convertDriverToDriverDTO(Driver driver) {
        return modelMapper.map(driver, DriverDTO.class);
    }

    public Driver convertDriverDTOToDriver(DriverDTO driverDTO) {
        return modelMapper.map(driverDTO, Driver.class);
    }

    public LoginDTO convertDriverToLoginDTO(Driver driver) {
        return modelMapper.map(driver, LoginDTO.class);
    }

    public Driver convertLoginDTOToDriver(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, Driver.class);
    }

}