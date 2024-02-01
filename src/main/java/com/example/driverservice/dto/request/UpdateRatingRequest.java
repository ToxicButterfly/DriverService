package com.example.driverservice.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingRequest {
    private Integer uId;
    private Float rating;
}
