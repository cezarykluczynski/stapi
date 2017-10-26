package com.cezarykluczynski.stapi.server.medical_condition.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper;
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper;
import com.cezarykluczynski.stapi.server.medical_condition.query.MedicalConditionRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionRestReader implements BaseReader<MedicalConditionRestBeanParams, MedicalConditionBaseResponse>,
		FullReader<String, MedicalConditionFullResponse> {

	private MedicalConditionRestQuery medicalConditionRestQuery;

	private MedicalConditionBaseRestMapper medicalConditionBaseRestMapper;

	private MedicalConditionFullRestMapper medicalConditionFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MedicalConditionRestReader(MedicalConditionRestQuery medicalConditionRestQuery,
			MedicalConditionBaseRestMapper medicalConditionBaseRestMapper, MedicalConditionFullRestMapper medicalConditionFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.medicalConditionRestQuery = medicalConditionRestQuery;
		this.medicalConditionBaseRestMapper = medicalConditionBaseRestMapper;
		this.medicalConditionFullRestMapper = medicalConditionFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MedicalConditionBaseResponse readBase(MedicalConditionRestBeanParams input) {
		Page<MedicalCondition> medicalConditionPage = medicalConditionRestQuery.query(input);
		MedicalConditionBaseResponse medicalConditionResponse = new MedicalConditionBaseResponse();
		medicalConditionResponse.setPage(pageMapper.fromPageToRestResponsePage(medicalConditionPage));
		medicalConditionResponse.setSort(sortMapper.map(input.getSort()));
		medicalConditionResponse.getMedicalConditions().addAll(medicalConditionBaseRestMapper.mapBase(medicalConditionPage.getContent()));
		return medicalConditionResponse;
	}

	@Override
	public MedicalConditionFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = new MedicalConditionRestBeanParams();
		medicalConditionRestBeanParams.setUid(uid);
		Page<MedicalCondition> medicalConditionPage = medicalConditionRestQuery.query(medicalConditionRestBeanParams);
		MedicalConditionFullResponse medicalConditionResponse = new MedicalConditionFullResponse();
		medicalConditionResponse.setMedicalCondition(medicalConditionFullRestMapper
				.mapFull(Iterables.getOnlyElement(medicalConditionPage.getContent(), null)));
		return medicalConditionResponse;
	}
}
