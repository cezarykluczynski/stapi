package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SeasonApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse;

@SuppressWarnings("ParameterNumber")
public class Season {

	private final SeasonApi seasonApi;

	private final String apiKey;

	public Season(SeasonApi seasonApi, String apiKey) {
		this.seasonApi = seasonApi;
		this.apiKey = apiKey;
	}

	public SeasonFullResponse get(String uid) throws ApiException {
		return seasonApi.seasonGet(uid, apiKey);
	}

	public SeasonBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer seasonNumberFrom,
			Integer seasonNumberTo, Integer numberOfEpisodesFrom, Integer numberOfEpisodesTo) throws ApiException {
		return seasonApi.seasonSearchPost(pageNumber, pageSize, sort, apiKey, title, seasonNumberFrom, seasonNumberTo, numberOfEpisodesFrom,
				numberOfEpisodesTo);
	}

}
