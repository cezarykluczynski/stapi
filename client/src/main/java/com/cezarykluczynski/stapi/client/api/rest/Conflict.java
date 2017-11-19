package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ConflictApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse;

@SuppressWarnings("ParameterNumber")
public class Conflict {

	private final ConflictApi conflictApi;

	private final String apiKey;

	public Conflict(ConflictApi conflictApi, String apiKey) {
		this.conflictApi = conflictApi;
		this.apiKey = apiKey;
	}

	public ConflictFullResponse get(String uid) throws ApiException {
		return conflictApi.conflictGet(uid, apiKey);
	}

	public ConflictBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Integer yearFrom, Integer yearTo,
			Boolean earthConflict, Boolean federationWar, Boolean klingonWar, Boolean dominionWarBattle, Boolean alternateReality)
			throws ApiException {
		return conflictApi.conflictSearchPost(pageNumber, pageSize, sort, apiKey, name, yearFrom, yearTo, earthConflict, federationWar, klingonWar,
				dominionWarBattle, alternateReality);
	}

}
