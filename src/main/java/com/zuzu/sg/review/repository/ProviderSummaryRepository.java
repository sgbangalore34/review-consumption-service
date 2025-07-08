package com.zuzu.sg.review.repository;

import com.zuzu.sg.review.entities.ProviderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderSummaryRepository extends JpaRepository<ProviderSummary, Long> {

}
