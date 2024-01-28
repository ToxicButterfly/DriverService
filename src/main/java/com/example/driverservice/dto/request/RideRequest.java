package com.example.driverservice.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private Integer rideId;
    private Integer driverId;
    private Float driverRating;
}
