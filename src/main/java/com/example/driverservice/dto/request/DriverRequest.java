package com.example.driverservice.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverRequest {
    private Integer id;
    private String token;
}

