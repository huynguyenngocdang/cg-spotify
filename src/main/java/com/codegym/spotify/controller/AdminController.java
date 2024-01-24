package com.codegym.spotify.controller;

import com.codegym.spotify.constant.VarConstant;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String displayAdminPage(Model model) {
        List<UserEntity> users = userService.getAllNonAdminUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", Arrays.asList(VarConstant.ROLE_TYPE_USER, VarConstant.ROLE_TYPE_MODERATOR));
        return "admin/admin";
    }

    @PostMapping("/admin/update-role")
    public String updateUserRole(@RequestParam Long userId,
                                 @RequestParam String role, RedirectAttributes redirectAttributes) {
        userService.updateUserRole(userId, role);
        return "redirect:/admin";
    }
}
