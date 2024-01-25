package com.codegym.spotify.controller;

import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.entity.UserProfile;
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

        UserProfileDto userProfileDto = userProfileService.findUserProfileByUserEntityId(userId);
        if (userProfileDto != null) {
            model.addAttribute("userProfile", userProfileDto);
            return "user/profile";
        } else {
            UserEntity user = userService.getCurrentUser();
            userProfileService.createUserProfileWithUserName(user.getUsername());
            return ("redirect:/user-profiles/{userId}/profile");
        }
    }


    @PostMapping("/user-profiles/{userId}/profile")
    public String enableEdit(@PathVariable("userId") Long userId, Model model) {
        UserProfileDto userProfileDto = userProfileService.findUserProfileByUserEntityId(userId);
        model.addAttribute("userProfile", userProfileDto);

        return "redirect:/user-profiles/{userId}/profile/edit";
    }

    @GetMapping("/user-profiles/{userId}/profile/edit")
    public String showForm(@PathVariable("userId") Long userId
            ,Model model) {
        UserProfileDto user = userProfileService.findUserProfileByUserEntityId(userId);
        model.addAttribute("userProfile", user);
        return "user/profile-edit";
    }

    @PostMapping("/user-profiles/{userId}/profile/edit")
    public String updateUserProfile(@PathVariable("userId") Long userId,
                                    @Valid @ModelAttribute("userProfile") UserProfileDto userProfileDto,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute(userProfileDto);
            return "user/profile-edit";
        }

        userProfileService.saveEditUserProfile(userProfileDto, userId);
        return "redirect:/user-profiles/{userId}/profile?editSuccess";
    }
}
