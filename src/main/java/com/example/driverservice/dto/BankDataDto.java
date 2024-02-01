package com.example.driverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankDataDto {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private Float balance;

}
