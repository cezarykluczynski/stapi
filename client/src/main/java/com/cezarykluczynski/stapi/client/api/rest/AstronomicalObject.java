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

	public AstronomicalObject(AstronomicalObjectApi astronomicalObjectApi) {
		this.astronomicalObjectApi = astronomicalObjectApi;
	}

	@Deprecated
	public AstronomicalObjectFullResponse get(String uid) throws ApiException {
		return astronomicalObjectApi.v1RestAstronomicalObjectGet(uid, null);
	}

	public AstronomicalObjectV2FullResponse getV2(String uid) throws ApiException {
		return astronomicalObjectApi.v2RestAstronomicalObjectGet(uid);
	}

	@Deprecated
	public AstronomicalObjectBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v1RestAstronomicalObjectSearchPost(pageNumber, pageSize, sort, null, name, astronomicalObjectType,
				locationUid);
	}

	public AstronomicalObjectV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v2RestAstronomicalObjectSearchPost(pageNumber, pageSize, sort, name, astronomicalObjectType,
				locationUid);
	}

}
