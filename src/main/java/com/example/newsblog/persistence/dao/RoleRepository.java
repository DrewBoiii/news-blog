package com.example.newsblog.persistence.dao;

import com.example.newsblog.persistence.model.Role;
import com.example.newsblog.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
