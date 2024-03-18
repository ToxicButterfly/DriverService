package com.example.driverservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.driverservice.util.Messages.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverCreateRequest {
    @NotEmpty(message = EMPTY_NAME_MESSAGE)
    private String fullName;
    @NotEmpty(message = EMPTY_USERNAME_MESSAGE)
    private String username;
    @Email(message = NON_VALID_EMAIL_MESSAGE)
    private String email;
}
