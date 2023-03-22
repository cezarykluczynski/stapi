package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SpacecraftClassV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftClassApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV3FullResponse;

@SuppressWarnings("ParameterNumber")
public class SpacecraftClass {

	private final SpacecraftClassApi spacecraftClassApi;

	public SpacecraftClass(SpacecraftClassApi spacecraftClassApi) {
		this.spacecraftClassApi = spacecraftClassApi;
	}

	@Deprecated
	public SpacecraftClassFullResponse get(String uid) throws ApiException {
		return spacecraftClassApi.v1GetSpacecraftClass(uid);
	}

	@Deprecated
	public SpacecraftClassV2FullResponse getV2(String uid) throws ApiException {
		return spacecraftClassApi.v2GetSpacecraftClass(uid);
	}

	public SpacecraftClassV3FullResponse getV3(String uid) throws ApiException {
		return spacecraftClassApi.v3GetSpacecraftClass(uid);
	}

	@Deprecated
	public SpacecraftClassBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapableSpecies,
			Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v1SearchSpacecraftClasses(pageNumber, pageSize, sort, name, warpCapableSpecies, alternateReality);
	}

	@Deprecated
	public SpacecraftClassV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean warpCapable,
			Boolean mirror, Boolean alternateReality) throws ApiException {
		return spacecraftClassApi.v2SearchSpacecraftClasses(pageNumber, pageSize, sort, name, warpCapable, mirror, alternateReality);
	}

	public SpacecraftClassV2BaseResponse searchV2(SpacecraftClassV2SearchCriteria spacecraftClassV2SearchCriteria) throws ApiException {
		return spacecraftClassApi.v2SearchSpacecraftClasses(spacecraftClassV2SearchCriteria.getPageNumber(),
				spacecraftClassV2SearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(spacecraftClassV2SearchCriteria.getSort()),
				spacecraftClassV2SearchCriteria.getName(), spacecraftClassV2SearchCriteria.getWarpCapable(),
				spacecraftClassV2SearchCriteria.getMirror(), spacecraftClassV2SearchCriteria.getAlternateReality());
	}

}
