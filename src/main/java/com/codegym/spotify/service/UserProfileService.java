package com.codegym.spotify.service;

import com.codegym.spotify.configuration.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> findAll();
    UserProfileDto findById(Long id);
}
