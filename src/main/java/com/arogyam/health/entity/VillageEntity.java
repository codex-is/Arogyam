package com.arogyam.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "villages")
public class VillageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    private Integer population;

    @Column(length = 50)
    private String primaryLanguage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public VillageEntity(){}

    public VillageEntity(String name, String district, String state){
        this.name = name;
        this.district = district;
        this.state = state;
    }

}
