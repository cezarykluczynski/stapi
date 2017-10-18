package com.cezarykluczynski.stapi.model.medical_condition.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MedicalConditionQueryBuilderFactory extends AbstractQueryBuilderFactory<MedicalCondition> {

	@Inject
	public MedicalConditionQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, MedicalCondition.class);
	}

}
