package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2FullResponse;

public class Spacecraft {

	private final SpacecraftApi spacecraftApi;

	public Spacecraft(SpacecraftApi spacecraftApi) {
		this.spacecraftApi = spacecraftApi;
	}

	@Deprecated
	public SpacecraftFullResponse get(String uid) throws ApiException {
		return spacecraftApi.v1RestSpacecraftGet(uid, null);
	}

	public SpacecraftV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftApi.v2RestSpacecraftGet(uid);
	}

	@Deprecated
	public SpacecraftBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name) throws ApiException {
		return spacecraftApi.v1RestSpacecraftSearchPost(pageNumber, pageSize, sort, null, name);
	}

	public SpacecraftV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String registry, String status)
			throws ApiException {
		return spacecraftApi.v2RestSpacecraftSearchPost(pageNumber, pageSize, sort, name, registry, status);
	}

}
