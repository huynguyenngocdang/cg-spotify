package com.codegym.spotify.service;

import com.codegym.spotify.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> findAll();
    UserProfileDto findById(Long id);

    void createNewUserProfile(UserProfileDto userProfileDto);

    boolean userProfileIsExist(Long id);
}
