package com.cezarykluczynski.stapi.server.medical_condition.query;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.model.medical_condition.dto.MedicalConditionRequestDTO;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionSoapQuery {

	private final MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper;

	private final MedicalConditionFullSoapMapper medicalConditionFullSoapMapper;

	private final PageMapper pageMapper;

	private final MedicalConditionRepository medicalConditionRepository;

	public MedicalConditionSoapQuery(MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper,
			MedicalConditionFullSoapMapper medicalConditionFullSoapMapper, PageMapper pageMapper,
			MedicalConditionRepository medicalConditionRepository) {
		this.medicalConditionBaseSoapMapper = medicalConditionBaseSoapMapper;
		this.medicalConditionFullSoapMapper = medicalConditionFullSoapMapper;
		this.pageMapper = pageMapper;
		this.medicalConditionRepository = medicalConditionRepository;
	}

	public Page<MedicalCondition> query(MedicalConditionBaseRequest medicalConditionBaseRequest) {
		MedicalConditionRequestDTO medicalConditionRequestDTO = medicalConditionBaseSoapMapper.mapBase(medicalConditionBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(medicalConditionBaseRequest.getPage());
		return medicalConditionRepository.findMatching(medicalConditionRequestDTO, pageRequest);
	}

	public Page<MedicalCondition> query(MedicalConditionFullRequest medicalConditionFullRequest) {
		MedicalConditionRequestDTO seriesRequestDTO = medicalConditionFullSoapMapper.mapFull(medicalConditionFullRequest);
		return medicalConditionRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
