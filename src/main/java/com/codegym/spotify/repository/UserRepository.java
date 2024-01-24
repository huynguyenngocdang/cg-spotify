package com.codegym.spotify.repository;

import com.codegym.spotify.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findFirstByUsername(String username);

    @Query("SELECT r.roleType FROM UserEntity u " +
            "JOIN u.roles r " +
            "WHERE u.username = :username ")
    List<String> findRoleByUsername(@Param("username") String username);

}
