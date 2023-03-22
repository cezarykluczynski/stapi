package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SpacecraftV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse;

public class Spacecraft {

	private final SpacecraftApi spacecraftApi;

	public Spacecraft(SpacecraftApi spacecraftApi) {
		this.spacecraftApi = spacecraftApi;
	}

	@Deprecated
	public SpacecraftFullResponse get(String uid) throws ApiException {
		return spacecraftApi.v1GetSpacecraft(uid);
	}

	public SpacecraftV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftApi.v2GetSpacecraft(uid);
	}

	@Deprecated
	public SpacecraftBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name) throws ApiException {
		return spacecraftApi.v1SearchSpacecrafts(pageNumber, pageSize, sort, name);
	}

	@Deprecated
	public SpacecraftV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String registry, String status)
			throws ApiException {
		return spacecraftApi.v2SearchSpacecrafts(pageNumber, pageSize, sort, name, registry, status);
	}

	public SpacecraftV2BaseResponse searchV2(SpacecraftV2SearchCriteria spacecraftV2SearchCriteria) throws ApiException {
		return spacecraftApi.v2SearchSpacecrafts(spacecraftV2SearchCriteria.getPageNumber(), spacecraftV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(spacecraftV2SearchCriteria.getSort()), spacecraftV2SearchCriteria.getName(),
				spacecraftV2SearchCriteria.getRegistry(), spacecraftV2SearchCriteria.getStatus());
	}

}
