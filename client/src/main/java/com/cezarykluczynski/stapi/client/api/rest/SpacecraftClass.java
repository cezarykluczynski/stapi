package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftClassApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse;

public class SpacecraftClass {

	private final SpacecraftClassApi spacecraftClassApi;

	private final String apiKey;

	public SpacecraftClass(SpacecraftClassApi spacecraftClassApi, String apiKey) {
		this.spacecraftClassApi = spacecraftClassApi;
		this.apiKey = apiKey;
	}

	public SpacecraftClassFullResponse get(String uid) throws ApiException {
		return spacecraftClassApi.spacecraftClassGet(uid, apiKey);
	}

	public SpacecraftClassBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.spacecraftClassSearchPost(pageNumber, pageSize, sort, apiKey, name, warpCapableSpecies, alternateReality);
	}

}
