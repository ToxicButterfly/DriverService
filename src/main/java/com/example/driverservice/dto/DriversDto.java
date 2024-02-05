package com.example.driverservice.dto;

import lombok.Data;

import java.util.List;

public record DriversDto(List<DriverDto> drivers) {
}
