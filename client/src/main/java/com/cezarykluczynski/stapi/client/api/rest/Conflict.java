package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ConflictApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Conflict {

	private final ConflictApi conflictApi;

	public Conflict(ConflictApi conflictApi) {
		this.conflictApi = conflictApi;
	}

	@Deprecated
	public ConflictFullResponse get(String uid) throws ApiException {
		return conflictApi.v1RestConflictGet(uid, null);
	}

	public ConflictV2FullResponse getV2(String uid) throws ApiException {
		return conflictApi.v2RestConflictGet(uid);
	}

	public ConflictBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Integer yearFrom, Integer yearTo,
			Boolean earthConflict, Boolean federationWar, Boolean klingonWar, Boolean dominionWarBattle, Boolean alternateReality)
			throws ApiException {
		return conflictApi.v1RestConflictSearchPost(pageNumber, pageSize, sort, null, name, yearFrom, yearTo, earthConflict, federationWar,
				klingonWar, dominionWarBattle, alternateReality);
	}

}
