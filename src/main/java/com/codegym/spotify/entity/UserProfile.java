package com.codegym.spotify.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Double balance;
    @CreationTimestamp
    private LocalDateTime createOn;

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
//    private Subscription subscription;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private UserEntity userEntity;
}
