package com.example.newsblog.service;

import com.example.newsblog.persistence.dto.user.RegistrationDto;
import com.example.newsblog.persistence.dto.user.UserCriteriaDto;
import com.example.newsblog.persistence.dto.user.UserProfileDto;
import com.example.newsblog.persistence.dto.user.UserRolesUpdateDto;
import com.example.newsblog.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {

    void save(RegistrationDto registrationDto);
    void update(UserProfileDto userProfileDto) throws IOException;
    void update(UserRolesUpdateDto userRolesUpdateDto);
    void deleteByUsername(String username);
    void deleteById(Long id);
    User getByUsername(String username);
    User getById(Long id);
    Page<User> getAll(Pageable pageable);
    Page<User> getAll(UserCriteriaDto criteria, Pageable pageable);
    Boolean isExists(String username, String email);

}
