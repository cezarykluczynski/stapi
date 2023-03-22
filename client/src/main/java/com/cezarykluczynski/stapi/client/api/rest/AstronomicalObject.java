package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.AstronomicalObjectV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class AstronomicalObject {

	private final AstronomicalObjectApi astronomicalObjectApi;

	public AstronomicalObject(AstronomicalObjectApi astronomicalObjectApi) {
		this.astronomicalObjectApi = astronomicalObjectApi;
	}

	@Deprecated
	public AstronomicalObjectFullResponse get(String uid) throws ApiException {
		return astronomicalObjectApi.v1GetAstronomicalObject(uid);
	}

	public AstronomicalObjectV2FullResponse getV2(String uid) throws ApiException {
		return astronomicalObjectApi.v2GetAstronomicalObject(uid);
	}

	@Deprecated
	public AstronomicalObjectBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v1SearchAstronomicalObjects(pageNumber, pageSize, sort, name, astronomicalObjectType,
				locationUid);
	}

	@Deprecated
	public AstronomicalObjectV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String astronomicalObjectType,
			String locationUid) throws ApiException {
		return astronomicalObjectApi.v2SearchAstronomicalObjects(pageNumber, pageSize, sort, name, astronomicalObjectType,
				locationUid);
	}

	public AstronomicalObjectV2BaseResponse searchV2(AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria) throws ApiException {
		return astronomicalObjectApi.v2SearchAstronomicalObjects(astronomicalObjectV2SearchCriteria.getPageNumber(),
				astronomicalObjectV2SearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(astronomicalObjectV2SearchCriteria.getSort()),
				astronomicalObjectV2SearchCriteria.getName(), nameIfPresent(astronomicalObjectV2SearchCriteria.getAstronomicalObjectType()),
				astronomicalObjectV2SearchCriteria.getLocationUid());
	}

	private static String nameIfPresent(AstronomicalObjectType astronomicalObjectType) {
		return astronomicalObjectType != null ? astronomicalObjectType.name() : null;
	}

}
