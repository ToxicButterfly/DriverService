package com.example.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.driverservice.util.Messages.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email(message = NON_VALID_EMAIL_MESSAGE)
    private String email;
    @Size(min = 6, message = NON_VALID_PASSWORD_MESSAGE)
    private String password;

}
