package com.zuzu.sg.review.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "provider_grade_ratings")
public class ProviderGradeRating {
    @EmbeddedId
    private ProviderGradeRatingId id;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @MapsId("gradeId")
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private Double actualRating;

    public ProviderGradeRating() {
    }

    public ProviderGradeRating(ProviderGradeRatingId id, Provider provider, Grade grade, Double actualRating) {
        this.id = id;
        this.provider = provider;
        this.grade = grade;
        this.actualRating = actualRating;
    }
}