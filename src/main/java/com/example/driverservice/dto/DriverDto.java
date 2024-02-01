package com.example.driverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private Integer id;
    private String fullName;
    private String username;
    private String email;
    private Date registerDate;
    private boolean availability;
    private Float rating;
}
