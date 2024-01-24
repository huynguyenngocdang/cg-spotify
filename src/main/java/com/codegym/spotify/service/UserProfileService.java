package com.codegym.spotify.service;

import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.entity.UserEntity;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> findAll();
    UserProfileDto findById(Long id) throws Exception;

    void createNewUserProfile(UserProfileDto userProfileDto);

    boolean userProfileIsExist(Long id);

    void updateUserProfile(String fullName, String email, String phoneNumber);

    void createUserProfileWithId(UserEntity userEntity);
}