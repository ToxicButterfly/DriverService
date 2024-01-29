package com.example.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 6, message = "Password must be atleast 6 symbold or longer")
    private String password;
}
