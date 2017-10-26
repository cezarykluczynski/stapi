package com.cezarykluczynski.stapi.model.medical_condition.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition_;
import com.cezarykluczynski.stapi.model.medical_condition.query.MedicalConditionQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalConditionRepositoryImpl implements MedicalConditionRepositoryCustom {

	private final MedicalConditionQueryBuilderFactory medicalConditionQueryBuilderFactory;

	public MedicalConditionRepositoryImpl(MedicalConditionQueryBuilderFactory medicalConditionQueryBuilderFactory) {
		this.medicalConditionQueryBuilderFactory = medicalConditionQueryBuilderFactory;
	}

	@Override
	public Page<MedicalCondition> findMatching(MedicalConditionRequestDTO criteria, Pageable pageable) {
		QueryBuilder<MedicalCondition> medicalConditionQueryBuilder = medicalConditionQueryBuilderFactory.createQueryBuilder(pageable);

		medicalConditionQueryBuilder.equal(MedicalCondition_.uid, criteria.getUid());
		medicalConditionQueryBuilder.like(MedicalCondition_.name, criteria.getName());
		medicalConditionQueryBuilder.equal(MedicalCondition_.psychologicalCondition, criteria.getPsychologicalCondition());
		medicalConditionQueryBuilder.setSort(criteria.getSort());

		return medicalConditionQueryBuilder.findPage();
	}

}
