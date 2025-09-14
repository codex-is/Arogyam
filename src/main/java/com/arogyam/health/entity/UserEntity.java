package com.arogyam.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 200)
    private String fullName;

    @Column(unique = true, nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserRole role;  //asha worker,  gov official,  general user

    @Column(nullable = false, length = 100)
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id")
    private VillageEntity village;

    @Column(length = 100)
    private String state = "Northeast India";

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    private LocalDateTime lastLogin;

    public UserEntity(){}

    public UserEntity(String username, String passwordHash, String fullName, String phoneNumber, UserRole role, String district){
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.district = district;
    }
}
