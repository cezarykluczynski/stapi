package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftClassApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class SpacecraftClass {

	private final SpacecraftClassApi spacecraftClassApi;

	private final String apiKey;

	public SpacecraftClass(SpacecraftClassApi spacecraftClassApi, String apiKey) {
		this.spacecraftClassApi = spacecraftClassApi;
		this.apiKey = apiKey;
	}

	@Deprecated
	public SpacecraftClassFullResponse get(String uid) throws ApiException {
		return spacecraftClassApi.v1RestSpacecraftClassGet(uid, apiKey);
	}

	public SpacecraftClassV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftClassApi.v2RestSpacecraftClassGet(uid, apiKey);
	}

	@Deprecated
	public SpacecraftClassBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v1RestSpacecraftClassSearchPost(pageNumber, pageSize, sort, apiKey, name, warpCapableSpecies, alternateReality);
	}

	public SpacecraftClassV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean mirror, Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v2RestSpacecraftClassSearchPost(pageNumber, pageSize, sort, apiKey, name, warpCapableSpecies, mirror,
				alternateReality);
	}

}
