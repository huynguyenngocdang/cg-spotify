package com.codegym.spotify.service.impl;

import com.codegym.spotify.configuration.dto.UserProfileDto;
import com.codegym.spotify.entity.UserProfile;
import com.codegym.spotify.repository.UserProfileRepository;
import com.codegym.spotify.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private UserProfileRepository userProfileRepository;
    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
    @Override
    public List<UserProfileDto> findAll() {
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        return userProfileList.stream().map(this::mapToUserProfileDto).collect(Collectors.toList());
    }

    @Override
    public UserProfileDto findById(Long id) {
        UserProfile userProfile = userProfileRepository.findById(id).get();
        return mapToUserProfileDto(userProfile);
    }

    public UserProfileDto mapToUserProfileDto(UserProfile userProfile){
        return UserProfileDto.builder()
                .id(userProfile.getId())
                .fullName(userProfile.getFullName())
                .email(userProfile.getPhoneNumber())
                .phoneNumber(userProfile.getPhoneNumber())
                .balance(userProfile.getBalance())
                .build();
    }
}
