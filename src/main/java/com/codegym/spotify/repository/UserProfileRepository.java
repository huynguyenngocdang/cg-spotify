package com.codegym.spotify.repository;

import com.codegym.spotify.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
