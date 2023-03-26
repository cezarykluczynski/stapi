package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2SearchCriteria;

public class Spacecraft {

	private final SpacecraftApi spacecraftApi;

	public Spacecraft(SpacecraftApi spacecraftApi) {
		this.spacecraftApi = spacecraftApi;
	}

	public SpacecraftV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftApi.v2GetSpacecraft(uid);
	}

	public SpacecraftV2BaseResponse searchV2(SpacecraftV2SearchCriteria spacecraftV2SearchCriteria) throws ApiException {
		return spacecraftApi.v2SearchSpacecrafts(spacecraftV2SearchCriteria.getPageNumber(), spacecraftV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(spacecraftV2SearchCriteria.getSort()), spacecraftV2SearchCriteria.getName(),
				spacecraftV2SearchCriteria.getRegistry(), spacecraftV2SearchCriteria.getStatus());
	}

}
