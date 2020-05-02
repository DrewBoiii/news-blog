package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") RegistrationDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, @AuthenticationPrincipal User authUser) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("user_dto", new UserProfileDto());
        return "private_profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user_dto") UserProfileDto userProfileDto,
                                @AuthenticationPrincipal User authUser) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        userProfileDto.setId(user.getId());
        userService.update(userProfileDto);
        return "redirect:/profile";
    }

    @GetMapping("/profile/{username}")
    public String getPublicProfile(@PathVariable("username") String username, Model model) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        return "public_profile";
    }

}
