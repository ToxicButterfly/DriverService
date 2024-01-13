package com.example.driverservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DriverDTO {
    private Integer id;
    private String fullName;
    private String username;
    private String email;
    private Date registerDate;
    private boolean availability;
    private Float rating;
}
