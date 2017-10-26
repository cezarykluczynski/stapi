package com.cezarykluczynski.stapi.server.medical_condition.query;

import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionRestQuery {

	private final MedicalConditionBaseRestMapper medicalConditionBaseRestMapper;

	private final PageMapper pageMapper;

	private final MedicalConditionRepository medicalConditionRepository;

	public MedicalConditionRestQuery(MedicalConditionBaseRestMapper medicalConditionBaseRestMapper, PageMapper pageMapper,
			MedicalConditionRepository medicalConditionRepository) {
		this.medicalConditionBaseRestMapper = medicalConditionBaseRestMapper;
		this.pageMapper = pageMapper;
		this.medicalConditionRepository = medicalConditionRepository;
	}

	public Page<MedicalCondition> query(MedicalConditionRestBeanParams medicalConditionRestBeanParams) {
		MedicalConditionRequestDTO medicalConditionRequestDTO = medicalConditionBaseRestMapper.mapBase(medicalConditionRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(medicalConditionRestBeanParams);
		return medicalConditionRepository.findMatching(medicalConditionRequestDTO, pageRequest);
	}

}
