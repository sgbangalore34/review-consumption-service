package com.zuzu.sg.review.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.identity.spi.Identity;

@Getter
@Setter
@Entity
@Table(name = "provider_summary")
public class ProviderSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long overallScoreId;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private Double overallScore;
    private Integer reviewCount;

    public ProviderSummary() {
    }

    public ProviderSummary(Long overallScoreId, Provider provider, Double overallScore, Integer reviewCount) {
        this.overallScoreId = overallScoreId;
        this.provider = provider;
        this.overallScore = overallScore;
        this.reviewCount = reviewCount;
    }
}
