package com.codegym.spotify.controller;

import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.UserProfileService;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    private UserProfileService userProfileService;
    private UserService userService;

    public ProfileController(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @GetMapping("/user-profiles/{userId}/profile")
    public String displayUserProfile(@PathVariable("userId") Long userId,
                                     Model model) {
        try {
            UserProfileDto userProfileDto = userProfileService.findById(userId);
            model.addAttribute("userProfile", userProfileDto);
            return "user/profile";
        }catch (Exception e){
            UserEntity user = userService.getCurrentUser();
            model.addAttribute(user);
            userProfileService.createUserProfileWithUserName();
            return "user/profile";
        }

    }
    @PostMapping("/user-profiles/{userId}/profile")
    public String enableEdit(@PathVariable("userId") Long userId) {
        return "redirect:/user-profiles/{userId}/profile/edit";
    }

    @GetMapping("/user-profiles/{userId}/profile/edit")
    public String showForm(Model model, UserProfileDto userProfileDto){
        model.addAttribute("userProfile",userProfileDto);
        return "user/profile-edit";
    }
    @PostMapping("/user-profiles/{userId}/profile/edit")
    public String updateUserProfile(@PathVariable("userId") Long userId,
                                    @Valid @ModelAttribute("userProfile") UserProfileDto userProfileDto,
                                    BindingResult result,
                                    Model model) {
        if(result.hasErrors()) {
            model.addAttribute(userProfileDto);
            return "user/profile-edit";
        }
        userProfileService.createNewUserProfile(userProfileDto);
        return "redirect:/index?addNewProfile";
    }

    @GetMapping("/user-profiles/{userId}/profile/show-form")
    public String showFormUserProfile(@PathVariable("userId") Long userId,Model model){
        model.addAttribute("userProfile", new UserProfileDto());
        return "user/profile-create";
    }

    @PostMapping("/user-profiles/{userId}/profile/create")
    public String creatUserProfile(@RequestParam String fullName,
                                   @RequestParam String email,
                                   @RequestParam String phoneNumber) {
        userProfileService.updateUserProfile(fullName,email,phoneNumber);
        return "redirect:/index";
    }
}
