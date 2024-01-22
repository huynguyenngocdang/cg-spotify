package com.codegym.spotify.configuration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
}
