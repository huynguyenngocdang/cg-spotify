package com.codegym.spotify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^0\\d{0,9}$", message = "Phone number Phone numbers can only have a maximum of 10 characters and must start with 0")
    private String phoneNumber;
    private Double balance;
    private Long userId;
}
