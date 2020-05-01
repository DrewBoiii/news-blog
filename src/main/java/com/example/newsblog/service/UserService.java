package com.example.newsblog.service;

import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.persistence.model.User;

import java.util.List;

public interface UserService {

    void save(RegistrationDto registrationDto);
    void update(UserProfileDto userProfileDto);
    void deleteByUsername(String username);
    void deleteById(Long id);
    User getByUsername(String username);
    User getById(Long id);
    List<User> getAll();

}
