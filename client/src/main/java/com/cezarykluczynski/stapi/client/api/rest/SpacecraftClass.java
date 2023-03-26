package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftClassApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV3FullResponse;

public class SpacecraftClass {

	private final SpacecraftClassApi spacecraftClassApi;

	public SpacecraftClass(SpacecraftClassApi spacecraftClassApi) {
		this.spacecraftClassApi = spacecraftClassApi;
	}

	public SpacecraftClassV3FullResponse getV3(String uid) throws ApiException {
		return spacecraftClassApi.v3GetSpacecraftClass(uid);
	}

	public SpacecraftClassV2BaseResponse searchV2(SpacecraftClassV2SearchCriteria spacecraftClassV2SearchCriteria) throws ApiException {
		return spacecraftClassApi.v2SearchSpacecraftClasses(spacecraftClassV2SearchCriteria.getPageNumber(),
				spacecraftClassV2SearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(spacecraftClassV2SearchCriteria.getSort()),
				spacecraftClassV2SearchCriteria.getName(), spacecraftClassV2SearchCriteria.getWarpCapableSpecies(),
				spacecraftClassV2SearchCriteria.getMirror(), spacecraftClassV2SearchCriteria.getAlternateReality());
	}

}
