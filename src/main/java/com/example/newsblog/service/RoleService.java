package com.example.newsblog.service;

import com.example.newsblog.persistence.model.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);
    Role getByName(String name);
    List<Role> getAll();

}
