package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type;

public class AstronomicalObject {

	private final AstronomicalObjectApi astronomicalObjectApi;

	public AstronomicalObject(AstronomicalObjectApi astronomicalObjectApi) {
		this.astronomicalObjectApi = astronomicalObjectApi;
	}

	public AstronomicalObjectV2FullResponse getV2(String uid) throws ApiException {
		return astronomicalObjectApi.v2GetAstronomicalObject(uid);
	}

	public AstronomicalObjectV2BaseResponse searchV2(AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria) throws ApiException {
		return astronomicalObjectApi.v2SearchAstronomicalObjects(astronomicalObjectV2SearchCriteria.getPageNumber(),
				astronomicalObjectV2SearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(astronomicalObjectV2SearchCriteria.getSort()),
				astronomicalObjectV2SearchCriteria.getName(), nameIfPresent(astronomicalObjectV2SearchCriteria.getAstronomicalObjectType()),
				astronomicalObjectV2SearchCriteria.getLocationUid());
	}

	private static String nameIfPresent(AstronomicalObjectV2Type astronomicalObjectType) {
		return astronomicalObjectType != null ? astronomicalObjectType.name() : null;
	}

}
