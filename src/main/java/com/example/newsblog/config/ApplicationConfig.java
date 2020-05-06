package com.example.newsblog.config;

import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.service.RoleService;
import com.example.newsblog.util.RoleConstants;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

    private final RoleService roleService;

    public ApplicationConfig(RoleService roleService) {
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
