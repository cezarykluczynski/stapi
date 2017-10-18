package com.cezarykluczynski.stapi.server.medical_condition.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper;
import com.cezarykluczynski.stapi.server.medical_condition.query.MedicalConditionSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionSoapReader implements BaseReader<MedicalConditionBaseRequest, MedicalConditionBaseResponse>,
		FullReader<MedicalConditionFullRequest, MedicalConditionFullResponse> {

	private final MedicalConditionSoapQuery medicalConditionSoapQuery;

	private final MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper;

	private final MedicalConditionFullSoapMapper medicalConditionFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MedicalConditionSoapReader(MedicalConditionSoapQuery medicalConditionSoapQuery,
			MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper, MedicalConditionFullSoapMapper medicalConditionFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.medicalConditionSoapQuery = medicalConditionSoapQuery;
		this.medicalConditionBaseSoapMapper = medicalConditionBaseSoapMapper;
		this.medicalConditionFullSoapMapper = medicalConditionFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MedicalConditionBaseResponse readBase(MedicalConditionBaseRequest input) {
		Page<MedicalCondition> medicalConditionPage = medicalConditionSoapQuery.query(input);
		MedicalConditionBaseResponse medicalConditionResponse = new MedicalConditionBaseResponse();
		medicalConditionResponse.setPage(pageMapper.fromPageToSoapResponsePage(medicalConditionPage));
		medicalConditionResponse.setSort(sortMapper.map(input.getSort()));
		medicalConditionResponse.getMedicalConditions().addAll(medicalConditionBaseSoapMapper.mapBase(medicalConditionPage.getContent()));
		return medicalConditionResponse;
	}

	@Override
	public MedicalConditionFullResponse readFull(MedicalConditionFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<MedicalCondition> medicalConditionPage = medicalConditionSoapQuery.query(input);
		MedicalConditionFullResponse medicalConditionFullResponse = new MedicalConditionFullResponse();
		medicalConditionFullResponse.setMedicalCondition(medicalConditionFullSoapMapper
				.mapFull(Iterables.getOnlyElement(medicalConditionPage.getContent(), null)));
		return medicalConditionFullResponse;
	}

}
