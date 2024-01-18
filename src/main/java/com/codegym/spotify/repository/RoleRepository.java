package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(String name);
}
