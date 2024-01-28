package com.example.driverservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingRequest {
    private Integer uId;
    private Float rating;
}
