package com.codegym.spotify.controller;

import com.codegym.spotify.dto.RegistrationDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("user", registrationDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result,
                           Model model) {
        UserEntity existingUsername = userService.findByUsername(user.getUsername());

        if(existingUsername != null
        && existingUsername.getUsername() != null
        && !existingUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }

        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());

        if(existingUserEmail != null
                && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/index?success";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/access-denied")
    public String showError(){
        return "redirect:/index";
    }

    @GetMapping("/account/change-password")
    public String showChangePasswordForm(Model model){
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "account/change-password";
    }
    @PostMapping("/account/change-password")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String passwordConfirm) {
        if( userService.checkExistingPassword(currentPassword)) {
           if( userService.passwordValid(newPassword, passwordConfirm)){
               userService.updatePassword(newPassword);
               return "redirect:/account/change-password";
           } else {
               return "redirect:/account/change-password?failSamePassword";
           }
        } else {
            return "redirect:/account/change-password?failExistPassword";
        }
    }
}
