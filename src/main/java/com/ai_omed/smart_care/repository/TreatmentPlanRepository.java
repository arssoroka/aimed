package com.ai_omed.smart_care.repository;

import com.ai_omed.smart_care.entity.TreatmentPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlanEntity, Long> {
}
