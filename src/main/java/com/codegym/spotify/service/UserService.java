package com.codegym.spotify.service;

import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.entity.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findById(Long userId);
    UserEntity getCurrentUser();
}
