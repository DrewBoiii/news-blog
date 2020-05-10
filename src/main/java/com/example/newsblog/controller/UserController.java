package com.example.newsblog.controller;

import com.example.newsblog.persistence.dto.captcha.CaptchaResponseDto;
import com.example.newsblog.persistence.dto.user.*;
import com.example.newsblog.service.CaptchaService;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/home/{username}")
    public String getPublicProfile(@PathVariable("username") String username, Model model) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("roles", new ArrayList<>(roleService.getAll()));
        model.addAttribute("role_dto", new UserRolesUpdateDto());
        return "public_profile";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('user')")
    public String getProfilePage(Model model, @AuthenticationPrincipal User authUser) {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("user_dto", new UserProfileDto());
        model.addAttribute("pass_dto", new ChangePasswordDto());
        return "private_profile";
    }

    @PostMapping("/profile/update")
    @PreAuthorize("hasAuthority('user')")
    public String updateProfile(@ModelAttribute("user_dto") UserProfileDto dto,
                                @AuthenticationPrincipal User authUser) throws IOException {
        com.example.newsblog.persistence.model.User user = userService.getByUsername(authUser.getUsername());
        userService.update(dto);
        return "redirect:/profile";
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
