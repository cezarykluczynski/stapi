package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ConflictApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ConflictSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.model.ConflictV2FullResponse;

public class Conflict {

	private final ConflictApi conflictApi;

	public Conflict(ConflictApi conflictApi) {
		this.conflictApi = conflictApi;
	}

	public ConflictV2FullResponse getV2(String uid) throws ApiException {
		return conflictApi.v2GetConflict(uid);
	}

	public ConflictBaseResponse search(ConflictSearchCriteria conflictSearchCriteria) throws ApiException {
		return conflictApi.v1SearchConflicts(conflictSearchCriteria.getPageNumber(), conflictSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(conflictSearchCriteria.getSort()), conflictSearchCriteria.getName(),
				conflictSearchCriteria.getYearFrom(), conflictSearchCriteria.getYearTo(), conflictSearchCriteria.getEarthConflict(),
				conflictSearchCriteria.getFederationWar(), conflictSearchCriteria.getKlingonWar(), conflictSearchCriteria.getDominionWarBattle(),
				conflictSearchCriteria.getAlternateReality());
	}

}
