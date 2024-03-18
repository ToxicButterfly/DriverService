package com.example.driverservice.util;

import com.example.driverservice.dto.BankDataDto;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.dto.request.UpdateRatingRequest;
import com.example.driverservice.model.Driver;
import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class TestUtils {

    public final Integer DEFAULT_ID = 1;
    public final String DEFAULT_FULLNAME = "Default fullname";
    public final String DEFAULT_EMAIL = "SomeEmail@Gmail.com";
    public final String DEFAULT_USERNAME = "Username";
    public final String DEFAULT_PASSWORD = "12345";
    public final Date DEFAULT_REGISTER_DATE = new Date(2024, Calendar.JANUARY, 1);
    public final boolean DEFAULT_AVAILABILITY_STATUS = true;
    public final boolean UNAVAILABLE_STATUS = false;
    public final Float DEFAULT_RATING = 3.0F;
    public final String INVALID_EMAIL = "WrongEmail@mail.ru";
    public final String INVALID_PASSWORD = "54321";

    public Driver getDefaultDriverToSave() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULLNAME)
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public Driver getDefaultDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULLNAME)
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .registerDate(DEFAULT_REGISTER_DATE)
                .availability(DEFAULT_AVAILABILITY_STATUS)
                .rating(DEFAULT_RATING)
                .build();
    }

    public Driver getUnavailableDriver() {
        return Driver.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULLNAME)
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .registerDate(DEFAULT_REGISTER_DATE)
                .availability(UNAVAILABLE_STATUS)
                .rating(DEFAULT_RATING)
                .build();
    }


    public DriverDto getDefaultDriverDto() {
        return DriverDto.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULLNAME)
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .registerDate(DEFAULT_REGISTER_DATE)
                .availability(DEFAULT_AVAILABILITY_STATUS)
                .rating(DEFAULT_RATING)
                .build();
    }

    public DriverDto getUnavailableDriverDto() {
        return DriverDto.builder()
                .id(DEFAULT_ID)
                .fullName(DEFAULT_FULLNAME)
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .registerDate(DEFAULT_REGISTER_DATE)
                .availability(UNAVAILABLE_STATUS)
                .rating(DEFAULT_RATING)
                .build();
    }

    public LoginDto getDefaultLogin() {
        return LoginDto.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    public LoginDto getDefaultInvalidLogin() {
        return LoginDto.builder()
                .email(INVALID_EMAIL)
                .password(INVALID_PASSWORD)
                .build();
    }

    public BankDataDto gedDefaultBankDataDto() {
        return BankDataDto.builder()
                .cvv("123")
                .cardNumber("1234567890123456")
                .expirationDate("12/26")
                .balance(90000F)
                .build();
    }

    public UpdateRatingRequest getDefaultUpdateRatingRequest() {
        return UpdateRatingRequest.builder()
                .uId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }
}
