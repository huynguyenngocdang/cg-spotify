package com.codegym.spotify.controller;

import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin")
    public String showUserTable(Model model){
        List<RegistrationDto> registrationDtos = userServiceImpl.findAllUserEntity();
        model.addAttribute("registrationDtos",registrationDtos);
        return "index/admin";
    }
}
