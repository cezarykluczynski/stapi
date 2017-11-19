package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse;

@SuppressWarnings("ParameterNumber")
public class Title {

	private final TitleApi titleApi;

	private final String apiKey;

	public Title(TitleApi titleApi, String apiKey) {
		this.titleApi = titleApi;
		this.apiKey = apiKey;
	}

	public TitleFullResponse get(String uid) throws ApiException {
		return titleApi.titleGet(uid, apiKey);
	}

	public TitleBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean position, Boolean mirror) throws ApiException {
		return titleApi.titleSearchPost(pageNumber, pageSize, sort, apiKey, name, militaryRank, fleetRank, religiousTitle, position, mirror);
	}

}
