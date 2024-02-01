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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Your name field must not be empty")
    private String fullName;
    @NotEmpty(message = "Your username field must not be empty")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 6, message = "Password must be atleast 6 symbold or longer")
    private String password;
    private Date registerDate;
    private boolean availability;
    private Float rating;
}
