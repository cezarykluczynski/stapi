package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.MedicalConditionSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.MedicalConditionApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MedicalConditionFullResponse;

public class MedicalCondition {

	private final MedicalConditionApi medicalConditionApi;

	public MedicalCondition(MedicalConditionApi medicalConditionApi) {
		this.medicalConditionApi = medicalConditionApi;
	}

	public MedicalConditionFullResponse get(String uid) throws ApiException {
		return medicalConditionApi.v1RestMedicalConditionGet(uid);
	}

	@Deprecated
	public MedicalConditionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean psychologicalCondition)
			throws ApiException {
		return medicalConditionApi.v1RestMedicalConditionSearchPost(pageNumber, pageSize, sort, name, psychologicalCondition);
	}

	public MedicalConditionBaseResponse search(MedicalConditionSearchCriteria medicalConditionSearchCriteria) throws ApiException {
		return medicalConditionApi.v1RestMedicalConditionSearchPost(medicalConditionSearchCriteria.getPageNumber(),
				medicalConditionSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(medicalConditionSearchCriteria.getSort()),
				medicalConditionSearchCriteria.getName(), medicalConditionSearchCriteria.getPsychologicalCondition());
	}

}
