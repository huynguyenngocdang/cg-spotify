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
public class RegistrationDto {
    private Long id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "Invalid username")
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$", message = "Invalid password")
    private String password;
    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9]{5,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}$", message = "Invalid email")
    private String email;
}
