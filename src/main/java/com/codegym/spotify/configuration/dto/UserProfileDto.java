package com.codegym.spotify.configuration.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserProfileDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Double balance;
}
