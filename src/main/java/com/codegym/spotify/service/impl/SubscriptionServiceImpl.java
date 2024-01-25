package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.SubscriptionDto;
import com.codegym.spotify.entity.Subscription;
import com.codegym.spotify.repository.SubscriptionRepository;
import com.codegym.spotify.service.SubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    @Override
    public List<SubscriptionDto> findAll() {
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        return subscriptionList.stream().map(this::mapToSubscriptionDto).collect(Collectors.toList());
    }

    public SubscriptionDto mapToSubscriptionDto(Subscription subscription){
        return SubscriptionDto.builder()
                .id(subscription.getId())
                .name(subscription.getName())
                .months(subscription.getMonths())
                .startDate(String.valueOf(subscription.getStartDate()))
                .endDate(String.valueOf(subscription.getEndDate()))
                .build();
    }
}
