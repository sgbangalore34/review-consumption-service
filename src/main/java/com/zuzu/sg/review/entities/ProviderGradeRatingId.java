package com.zuzu.sg.review.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProviderGradeRatingId implements Serializable {
    private Long providerId;
    private Long gradeId;

    public ProviderGradeRatingId() {
    }

    public ProviderGradeRatingId(Long providerId, Long gradeId) {
        this.providerId = providerId;
        this.gradeId = gradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderGradeRatingId that = (ProviderGradeRatingId) o;
        return Objects.equals(providerId, that.providerId) &&
                Objects.equals(gradeId, that.gradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, gradeId);
    }
}
