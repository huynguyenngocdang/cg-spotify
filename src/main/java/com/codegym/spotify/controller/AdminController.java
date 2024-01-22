package com.codegym.spotify.controller;

import com.codegym.spotify.configuration.dto.RegistrationDto;
import com.codegym.spotify.configuration.dto.UserProfileDto;
import com.codegym.spotify.service.impl.UserProfileServiceImpl;
import com.codegym.spotify.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImpl userServiceImpl;
    private UserProfileServiceImpl userProfileService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")
    public String showUserTable(Model model){
        List<RegistrationDto> registrationDtos = userServiceImpl.findAllUserEntity();
        model.addAttribute("user",registrationDtos);
        return "user/admin";
    }

    @GetMapping("/detail")
    public String showAdminProfile(@AuthenticationPrincipal UserProfileDto userProfileDto,Model model){
        model.addAttribute(userProfileDto);
        return "user/profile";
    }
}
