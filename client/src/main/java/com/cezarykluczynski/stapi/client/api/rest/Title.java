package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Title {

	private final TitleApi titleApi;

	private final String apiKey;

	public Title(TitleApi titleApi, String apiKey) {
		this.titleApi = titleApi;
		this.apiKey = apiKey;
	}

	@Deprecated
	public TitleFullResponse get(String uid) throws ApiException {
		return titleApi.v1RestTitleGet(uid, apiKey);
	}

	public TitleV2FullResponse getV2(String uid) throws ApiException {
		return titleApi.v2RestTitleGet(uid, apiKey);
	}

	@Deprecated
	public TitleBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean position, Boolean mirror) throws ApiException {
		return titleApi.v1RestTitleSearchPost(pageNumber, pageSize, sort, apiKey, name, militaryRank, fleetRank, religiousTitle, position, mirror);
	}

	public TitleV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean militaryRank, Boolean fleetRank,
			Boolean religiousTitle, Boolean educationTitle, Boolean mirror) throws ApiException {
		return titleApi.v2RestTitleSearchPost(pageNumber, pageSize, sort, apiKey, name, militaryRank, fleetRank, religiousTitle, educationTitle,
				mirror);
	}

}
