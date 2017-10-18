package com.cezarykluczynski.stapi.model.medical_condition.repository;

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long>, MedicalConditionRepositoryCustom {
}
