package com.example.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    public final String INVALID_LOGIN_MESSAGE = "Username or Email is already taken";
    public final String DRIVER_NOT_FOUND_MESSAGE = "There's no such driver";
    public final String CVV = "123";
    public final String CARD_NUMBER = "1234567890123456";
    public final String EXPIRATION_DATE = "12/26";
    public final Float BALANCE = 90000F;
    public final String EMPTY_NAME_MESSAGE = "Your name field must not be empty";
    public final String EMPTY_USERNAME_MESSAGE = "Your username field must not be empty";
    public final String NON_VALID_EMAIL_MESSAGE = "Email should be valid";
    public final String NON_VALID_PASSWORD_MESSAGE = "Password must be at least 6 symbols or longer";
}
