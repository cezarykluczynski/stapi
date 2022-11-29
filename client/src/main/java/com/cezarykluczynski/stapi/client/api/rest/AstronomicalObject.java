package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class AstronomicalObject {

	private final AstronomicalObjectApi astronomicalObjectApi;

	private final String apiKey;

	public AstronomicalObject(AstronomicalObjectApi astronomicalObjectApi, String apiKey) {
		this.astronomicalObjectApi = astronomicalObjectApi;
		this.apiKey = apiKey;
	}

	@Deprecated
	public AstronomicalObjectFullResponse get(String uid) throws ApiException {
		return astronomicalObjectApi.v1RestAstronomicalObjectGet(uid, apiKey);
	}

	public AstronomicalObjectV2FullResponse getV2(String uid) throws ApiException {
		return astronomicalObjectApi.v2RestAstronomicalObjectGet(uid, apiKey);
	}

	@Deprecated
	public AstronomicalObjectBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v1RestAstronomicalObjectSearchPost(pageNumber, pageSize, sort, apiKey, name, astronomicalObjectType,
				locationUid);
	}

	public AstronomicalObjectV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v2RestAstronomicalObjectSearchPost(pageNumber, pageSize, sort, apiKey, name, astronomicalObjectType,
				locationUid);
	}

}
