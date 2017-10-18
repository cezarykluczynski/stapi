package com.cezarykluczynski.stapi.model.medical_condition.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;

public interface MedicalConditionRepositoryCustom extends CriteriaMatcher<MedicalConditionRequestDTO, MedicalCondition> {
}
