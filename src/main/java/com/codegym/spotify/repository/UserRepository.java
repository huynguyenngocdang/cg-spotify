package com.codegym.spotify.repository;

import com.codegym.spotify.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findFirstByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u NOT IN (SELECT ur FROM UserEntity ur JOIN ur.roles r WHERE r.roleType = 'ROLE_ADMIN')")
    List<UserEntity> findAllNonAdminUsers();

}
