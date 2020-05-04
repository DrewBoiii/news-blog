package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserCriteriaDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.persistence.dto.user.UserRolesUpdateDto;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Controller
public class UserController {

    public static final int ITEMS_PER_PAGE = 5;

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
    public String saveUser(Model model, @ModelAttribute("user") RegistrationDto registrationDto) {
        if(!userService.isExists(registrationDto.getUsername(), registrationDto.getEmail())) {
            userService.save(registrationDto);
            return "redirect:/login";
        }
        model.addAttribute("message", "User with such username or email is already exists");
        return "registration";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('user')")
    public String getProfilePage(Model model, @AuthenticationPrincipal User authUser) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("user_dto", new UserProfileDto());
        return "private_profile";
    }

    @PostMapping("/profile/update")
    @PreAuthorize("hasAuthority('user')")
    public String updateProfile(@ModelAttribute("user_dto") UserProfileDto userProfileDto,
                                @AuthenticationPrincipal User authUser) throws IOException {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        userService.update(userProfileDto);
        return "redirect:/profile";
    }

    @GetMapping("/home/{username}")
    public String getPublicProfile(@PathVariable("username") String username, Model model) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("roles", new ArrayList<>(roleService.getAll()));
        model.addAttribute("role_dto", new UserRolesUpdateDto());
        return "public_profile";
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public String getUsersPage(Model model,
                               @ModelAttribute("criteria") UserCriteriaDto criteria,
                               @PageableDefault(value = ITEMS_PER_PAGE) Pageable pageable,
                               @AuthenticationPrincipal User user) {
        Sort sort = Sort.by(criteria.getSort()).descending();
        if(criteria.getOrder().equals("asc")) {
            sort = Sort.by(criteria.getSort()).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        model.addAttribute("page", userService.getAll(criteria, pageRequest));
        return "users";
    }

    @PostMapping("/users/roles/update")
    @PreAuthorize("hasAuthority('admin')")
    public String updateUserRoles(@ModelAttribute("role_dto") UserRolesUpdateDto userRolesUpdateDto,
                                  @AuthenticationPrincipal User user) {
        userService.update(userRolesUpdateDto);
        return "redirect:/users";
    }

}
