package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse;

@SuppressWarnings("ParameterNumber")
public class AstronomicalObject {

	private final AstronomicalObjectApi astronomicalObjectApi;

	private final String apiKey;

	public AstronomicalObject(AstronomicalObjectApi astronomicalObjectApi, String apiKey) {
		this.astronomicalObjectApi = astronomicalObjectApi;
		this.apiKey = apiKey;
	}

	public AstronomicalObjectFullResponse get(String uid) throws ApiException {
		return astronomicalObjectApi.astronomicalObjectGet(uid, apiKey);
	}

	public AstronomicalObjectBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.astronomicalObjectSearchPost(pageNumber, pageSize, sort, apiKey, name, astronomicalObjectType, locationUid);
	}

}
