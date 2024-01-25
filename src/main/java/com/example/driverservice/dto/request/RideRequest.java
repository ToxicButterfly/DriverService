package com.example.driverservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RideRequest {
    private Integer rideId;
    private Integer driverId;
    private Float driverRating;
}
