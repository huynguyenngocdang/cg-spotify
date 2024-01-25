package com.codegym.spotify.service;

import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.entity.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    List<RegistrationDto> findAllUserEntity();
    RegistrationDto findById(Long userId);
    UserEntity getCurrentUser();

    void updatePassword(String newPassword);

    boolean checkExistingPassword(String currentPassword);
    boolean passwordValid(String password1, String password2);
    List<UserEntity> getAllNonAdminUsers();

    void updateUserRole(Long userId, String role);
}
