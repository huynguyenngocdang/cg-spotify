package com.codegym.spotify.service.impl;

import com.codegym.spotify.constant.VarConstant;
import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.entity.Role;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.repository.RoleRepository;
import com.codegym.spotify.repository.UserRepository;
import com.codegym.spotify.security.SecurityUtil;
import com.codegym.spotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user =new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        Role role = roleRepository.findByRoleType(VarConstant.ROLE_TYPE_USER);
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String newPassword) {
        UserEntity user = getCurrentUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<RegistrationDto> findAllUserEntity() {
        List<UserEntity> registrationDtos = userRepository.findAll();
        return registrationDtos.stream().map(this::mapToRegistrationDto).collect(Collectors.toList());
    }

    protected RegistrationDto mapToRegistrationDto(UserEntity userEntity){
        RegistrationDto registrationDto = RegistrationDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .build();
        return registrationDto;
    }

    public RegistrationDto findById(Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        return mapToRegistrationDto(user);
    }
    @Override
    public UserEntity getCurrentUser(){
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = findByUsername(username);
        }
        return user;
    }

    @Override
    public boolean checkExistingPassword(String currentEnterPassword) {
        UserEntity userEntity = getCurrentUser();
        String currentUserPassword = userEntity.getPassword();
        return passwordEncoder.matches(currentEnterPassword, currentUserPassword);
    }

    @Override
    public boolean passwordValid(String password1, String password2) {
        return password1.equals(password2);
    }

}
