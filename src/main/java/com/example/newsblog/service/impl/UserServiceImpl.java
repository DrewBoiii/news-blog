package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dao.UserRepository;
import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserCriteriaDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.persistence.dto.user.UserRolesUpdateDto;
import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.persistence.model.User;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.service.UserService;
import com.example.newsblog.specification.UserSpecification;
import com.example.newsblog.util.ImageUploadUtil;
import com.example.newsblog.util.RoleConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void save(RegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setRoles(Collections.singleton(roleService.getByName(RoleConstants.USER_ROLE)));
        user.setBirth(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void update(UserProfileDto userProfileDto) throws IOException {
        User user = userRepository.findById(userProfileDto.getId()).orElse(null);
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        LocalDate date = LocalDate.parse(userProfileDto.getBirth());
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        user.setBirth(dateTime);

        byte[] userPhoto;
        try {
            userPhoto = ImageUploadUtil.avatarConvert(userProfileDto.getPhoto());
            if (userPhoto == null) {
                log.error("PHOTO IS NULL");
            }
            user.setPhoto(userPhoto);
        } catch (IOException e) {
            throw new IOException();
        }

        userRepository.save(user);
    }

    @Override
    public void update(UserRolesUpdateDto userRolesUpdateDto) {
        User user = userRepository.findById(userRolesUpdateDto.getId()).orElse(null);
        Set<Role> roles = user.getRoles();
        List<String> roleNames = userRolesUpdateDto.getRoles();
        roleNames.forEach(roleName -> roles.add(roleService.getByName(roleName)));
        userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getAll(UserCriteriaDto criteria, Pageable pageable) {
        return userRepository.findAll(
                Specification
                        .where(UserSpecification.getUsersByUsername(criteria.getUsername()))
                        .and(UserSpecification.getUsersByFirstName(criteria.getFirstName()))
                        .and(UserSpecification.getUsersByLastName(criteria.getLastName())),
                pageable
        );
    }
}
