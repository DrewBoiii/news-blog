package com.example.newsblog.config;

import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.util.RoleConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitOnStartupConfigTemp {

    private final RoleService roleService;

    public InitOnStartupConfigTemp(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        if(roleService.getAll().isEmpty()) {
            Role userRole = new Role(RoleConstants.USER_ROLE);
            Role moderRole = new Role(RoleConstants.MODER_ROLE);
            Role adminRole = new Role(RoleConstants.ADMIN_ROLE);
            roleService.save(userRole);
            roleService.save(moderRole);
            roleService.save(adminRole);
        }
    }

}
