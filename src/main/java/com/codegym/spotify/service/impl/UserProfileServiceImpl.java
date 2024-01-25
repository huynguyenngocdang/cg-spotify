package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.entity.UserProfile;
import com.codegym.spotify.repository.UserProfileRepository;
import com.codegym.spotify.repository.UserRepository;
import com.codegym.spotify.service.UserProfileService;
import com.codegym.spotify.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private UserService userService;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper, UserRepository userRepository, UserService userService) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<UserProfileDto> findAll() {
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        return userProfileList.stream().map(this::convertToProfileDto).collect(Collectors.toList());
    }

    @Override
    public UserProfileDto findUserProfileByUserEntityId(Long userEntityId) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserEntityId(userEntityId);
        if (userProfile != null) {
            return convertToProfileDto(userProfile);
        } else {
            return null;
        }
    }
    @Override
    public void createNewUserProfile(UserProfileDto userProfileDto) {
        UserProfile userProfile = convertToProfileEntity(userProfileDto);
        userProfileRepository.save(userProfile);
    }

    @Override
    public boolean userProfileIsExist(Long id) {
        Optional<UserProfile> userProfileDto = userProfileRepository.findById(id);
        return userProfileDto != null;
    }

    public UserProfileDto mapToUserProfileDto(UserProfile userProfile){
        return UserProfileDto.builder()
                .id(userProfile.getId())
                .fullName(userProfile.getFullName())
                .email(userProfile.getEmail())
                .phoneNumber(userProfile.getPhoneNumber())
                .balance(userProfile.getBalance())
                .build();
    }

    private UserProfileDto convertToProfileDto(UserProfile userProfile) {
        UserProfileDto userProfileDto = modelMapper.map(userProfile, UserProfileDto.class);
        if(userProfile.getUserEntity() != null) {
            userProfileDto.setUserId(userProfile.getUserEntity().getId());
        }
        return userProfileDto;
    }

    private UserProfile convertToProfileEntity(UserProfileDto userProfileDto) {
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        if(userProfileDto.getUserId() != null) {
            UserEntity userEntity = userRepository.findById(userProfileDto.getUserId()).get();
            userProfile.setUserEntity(userEntity);
        }
        return userProfile;
    }

    @Override
    public void updateUserProfile(String fullName, String email, String phoneNumber) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(fullName);
        userProfile.setEmail(email);
        userProfile.setPhoneNumber(phoneNumber);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void createUserProfileWithUserName(String username) {
        UserProfile userProfile = new UserProfile();
        UserEntity user = userService.findByUsername(username);
        userProfile.setUserEntity(user);
        userProfile.setFullName(username);
        userProfile.setEmail(user.getEmail());
        userProfile.setBalance((double) 0);
        userProfile.setPhoneNumber("");
        userProfileRepository.save(userProfile);
    }

    @Override
    public void saveEditUserProfile(UserProfileDto userProfileDto, Long userId) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserEntityId(userId);
        userProfile.setFullName(userProfileDto.getFullName());
        userProfile.setEmail(userProfileDto.getEmail());
        userProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        userProfile.setCreateOn(LocalDateTime.now());
        userProfileRepository.save(userProfile);
    }
}
