package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dao.UserRepository;
import com.example.newsblog.persistence.dto.user.*;
import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.persistence.model.User;
import com.example.newsblog.service.MailService;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.service.UserService;
import com.example.newsblog.specification.UserSpecification;
import com.example.newsblog.util.ImageUploadUtil;
import com.example.newsblog.util.MailUtil;
import com.example.newsblog.util.RoleConstants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, MailService mailService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.mailService = mailService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(RegistrationDto dto) {
        User user = modelMapper.map(dto, User.class);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singleton(roleService.getByName(RoleConstants.USER_ROLE)));
        user.setBirth(LocalDateTime.now());
        user.setActivationCode(MailUtil.getGeneratedCode());

        userRepository.save(user);

        String message = String.format(
                "Hello, %s! \n" +
                "Welcome to the News Blog!\n" +
                "Please follow the link below to activate your account\n" +
                "http://localhost:8080/activation/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        mailService.send(user.getEmail(), "Account activation", message);
    }

    @Override
    public void update(UserProfileDto dto) throws IOException {
        User user = userRepository.findById(dto.getId()).orElse(null);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        LocalDate date = LocalDate.parse(dto.getBirth());
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);
        user.setBirth(dateTime);

        byte[] userPhoto;
        try {
            userPhoto = ImageUploadUtil.avatarConvert(dto.getPhoto());
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
    public void update(ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(changePasswordDto.getId()).orElse(null);
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
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

    @Override
    public Boolean isExists(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public Boolean isActivate(String code) {
        User user = userRepository.findByActivationCode(code).orElse(null);

        if(user != null) {
            user.setActive(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
