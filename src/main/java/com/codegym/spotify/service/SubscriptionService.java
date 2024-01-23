package com.codegym.spotify.service;

import com.codegym.spotify.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> findAll();
}
