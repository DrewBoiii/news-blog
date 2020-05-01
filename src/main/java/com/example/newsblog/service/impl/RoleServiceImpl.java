package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dao.RoleRepository;
import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
