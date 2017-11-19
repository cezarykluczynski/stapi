package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MedicalConditionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse;

public class MedicalCondition {

	private final MedicalConditionApi medicalConditionApi;

	private final String apiKey;

	public MedicalCondition(MedicalConditionApi medicalConditionApi, String apiKey) {
		this.medicalConditionApi = medicalConditionApi;
		this.apiKey = apiKey;
	}

	public MedicalConditionFullResponse get(String uid) throws ApiException {
		return medicalConditionApi.medicalConditionGet(uid, apiKey);
	}

	public MedicalConditionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean psychologicalCondition)
			throws ApiException {
		return medicalConditionApi.medicalConditionSearchPost(pageNumber, pageSize, sort, apiKey, name, psychologicalCondition);
	}

}
