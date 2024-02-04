package com.example.driverservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.example.driverservice.util.Messages.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = EMPTY_NAME_MESSAGE)
    private String fullName;
    @NotEmpty(message = EMPTY_USERNAME_MESSAGE)
    private String username;
    @Email(message = NON_VALID_EMAIL_MESSAGE)
    private String email;
    @Size(min = 6, message = NON_VALID_PASSWORD_MESSAGE)
    private String password;
    private Date registerDate;
    private boolean availability;
    private Float rating;
}
