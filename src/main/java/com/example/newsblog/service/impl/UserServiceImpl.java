package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dao.UserRepository;
import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.persistence.model.User;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.service.UserService;
import com.example.newsblog.util.RoleConstants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Role role = new Role();
        role.setName(RoleConstants.USER_ROLE);
        roleService.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void update(UserProfileDto userProfileDto) {
        User user = userRepository.findById(userProfileDto.getId()).orElse(null);
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        LocalDate date = LocalDate.parse(userProfileDto.getBirth());
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        user.setBirth(dateTime);
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
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
