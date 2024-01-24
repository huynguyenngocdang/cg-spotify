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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId).get();
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
    public List<UserEntity> getAllNonAdminUsers() {
        return userRepository.findAllNonAdminUsers();
    }

    public void updateUserRole(Long userId, String role) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role newRole = roleRepository.findByRoleType(role);

        // Clear the current roles and add the new role
        user.getRoles().clear();
        user.getRoles().add(newRole);

        userRepository.save(user);
    }
}
