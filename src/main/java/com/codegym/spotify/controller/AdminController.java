package com.codegym.spotify.controller;

import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.security.SecurityUtil;
import com.codegym.spotify.service.UserService;
import com.codegym.spotify.service.impl.UserProfileServiceImpl;
import com.codegym.spotify.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    private UserServiceImpl userServiceImpl;
    private UserProfileServiceImpl userProfileService;
    private UserService userService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, UserProfileServiceImpl userProfileService, UserService userService) {
        this.userServiceImpl = userServiceImpl;
        this.userProfileService = userProfileService;
        this.userService = userService;
    }



    @GetMapping("/admin/user-list")
    public String showUserTable(Model model){
        List<RegistrationDto> registrationDtos = userServiceImpl.findAllUserEntity();
        model.addAttribute("user2",registrationDtos);

        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
        }
        model.addAttribute("user", user);

        return "user/admin";
    }

    @GetMapping("/detail")
    public String showAdminProfile(@AuthenticationPrincipal UserProfileDto userProfileDto,Model model){
        model.addAttribute(userProfileDto);
        return "user/profile";
    }
}
