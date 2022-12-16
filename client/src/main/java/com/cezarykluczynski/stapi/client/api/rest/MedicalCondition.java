package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MedicalConditionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse;

public class MedicalCondition {

	private final MedicalConditionApi medicalConditionApi;

	public MedicalCondition(MedicalConditionApi medicalConditionApi) {
		this.medicalConditionApi = medicalConditionApi;
	}

	public MedicalConditionFullResponse get(String uid) throws ApiException {
		return medicalConditionApi.v1RestMedicalConditionGet(uid, null);
	}

	public MedicalConditionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean psychologicalCondition)
			throws ApiException {
		return medicalConditionApi.v1RestMedicalConditionSearchPost(pageNumber, pageSize, sort, null, name, psychologicalCondition);
	}

}
