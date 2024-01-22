package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
}
