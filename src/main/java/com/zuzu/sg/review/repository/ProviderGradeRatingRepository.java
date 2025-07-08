package com.zuzu.sg.review.repository;

import com.zuzu.sg.review.entities.ProviderGradeRating;
import com.zuzu.sg.review.entities.ProviderGradeRatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderGradeRatingRepository extends JpaRepository<ProviderGradeRating, ProviderGradeRatingId> {
}
