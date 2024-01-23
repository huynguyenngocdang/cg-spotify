package com.codegym.spotify.controller;

import com.codegym.spotify.dto.UserProfileDto;
import com.codegym.spotify.service.UserProfileService;
import groovyjarjarasm.asm.tree.TryCatchBlockNode;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {
    private UserProfileService userProfileService;

    public ProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/user-profiles/{userId}/profile")
    public String displayUserProfile(@PathVariable("userId") Long userId,
                                     Model model) {
            UserProfileDto userProfileDto = userProfileService.findByUserEntityId(userId);
            model.addAttribute("userProfile",userProfileDto);
            return "user/profile";
    }

    @PostMapping("/user-profiles/{userId}/profile")
    public String enableEdit(@PathVariable("userId") Long userId) {
        return "redirect:/userProfiles/{userId}/profile/edit";
    }

    @PutMapping("/userProfiles/{userId}/profile/edit")
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

    @GetMapping("/user-profiles/{userId}/profile/create")
    public String showFormUserProfile(@PathVariable("userId") Long userI){
        return "user/profile-create";
    }

    @PostMapping("/user-profiles/{userId}/profile/create")
    public String creatUserProfile(@PathVariable("userId") Long userId,
                                 @Valid @ModelAttribute("userProfile") UserProfileDto userProfileDto,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()) {
            model.addAttribute(userProfileDto);
            return "user/profile-create";
        }

        userProfileService.createNewUserProfile(userProfileDto);
        return "redirect:/index?addNewProfile";
    }



}
