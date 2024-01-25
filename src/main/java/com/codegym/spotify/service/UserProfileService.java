package com.codegym.spotify.service;

import com.codegym.spotify.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> findAll();
    UserProfileDto findUserProfileByUserEntityId(Long id);

    void createNewUserProfile(UserProfileDto userProfileDto);

    boolean userProfileIsExist(Long id);

    void updateUserProfile(String fullName, String email, String phoneNumber);

    void createUserProfileWithUserName(String username);

    void saveEditUserProfile(UserProfileDto userProfileDto, Long userId);
}
