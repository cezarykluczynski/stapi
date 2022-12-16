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

	public SpacecraftClass(SpacecraftClassApi spacecraftClassApi) {
		this.spacecraftClassApi = spacecraftClassApi;
	}

	@Deprecated
	public SpacecraftClassFullResponse get(String uid) throws ApiException {
		return spacecraftClassApi.v1RestSpacecraftClassGet(uid, null);
	}

	public SpacecraftClassV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftClassApi.v2RestSpacecraftClassGet(uid);
	}

	@Deprecated
	public SpacecraftClassBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v1RestSpacecraftClassSearchPost(pageNumber, pageSize, sort, null, name, warpCapableSpecies, alternateReality);
	}

	public SpacecraftClassV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean mirror, Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v2RestSpacecraftClassSearchPost(pageNumber, pageSize, sort, name, warpCapableSpecies, mirror,
				alternateReality);
	}

}
