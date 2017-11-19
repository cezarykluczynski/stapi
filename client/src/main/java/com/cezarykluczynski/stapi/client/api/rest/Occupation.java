package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.OccupationApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse;

@SuppressWarnings("ParameterNumber")
public class Occupation {

	private final OccupationApi occupationApi;

	private final String apiKey;

	public Occupation(OccupationApi occupationApi, String apiKey) {
		this.occupationApi = occupationApi;
		this.apiKey = apiKey;
	}

	public OccupationFullResponse get(String uid) throws ApiException {
		return occupationApi.occupationGet(uid, apiKey);
	}

	public OccupationBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean legalOccupation,
			Boolean medicalOccupation, Boolean scientificOccupation) throws ApiException {
		return occupationApi.occupationSearchPost(pageNumber, pageSize, sort, apiKey, name, legalOccupation, medicalOccupation, scientificOccupation);
	}

}
