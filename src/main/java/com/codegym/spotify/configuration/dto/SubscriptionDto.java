package com.codegym.spotify.configuration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {
    private Long id;
    private String name;
    private Integer months;
    private String startDate;
    private String endDate;
}
