package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse;

public class Spacecraft {

	private final SpacecraftApi spacecraftApi;

	private final String apiKey;

	public Spacecraft(SpacecraftApi spacecraftApi, String apiKey) {
		this.spacecraftApi = spacecraftApi;
		this.apiKey = apiKey;
	}

	public SpacecraftFullResponse get(String uid) throws ApiException {
		return spacecraftApi.spacecraftGet(uid, apiKey);
	}

	public SpacecraftBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name) throws ApiException {
		return spacecraftApi.spacecraftSearchPost(pageNumber, pageSize, sort, apiKey, name);
	}

}
