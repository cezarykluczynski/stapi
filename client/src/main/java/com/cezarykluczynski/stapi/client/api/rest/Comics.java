package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse;

@SuppressWarnings("ParameterNumber")
public class Comics {

	private final ComicsApi comicsApi;

	private final String apiKey;

	public Comics(ComicsApi comicsApi, String apiKey) {
		this.comicsApi = comicsApi;
		this.apiKey = apiKey;
	}

	public ComicsFullResponse get(String uid) throws ApiException {
		return comicsApi.comicsGet(uid, apiKey);
	}

	public ComicsBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean photonovel, Boolean adaptation) throws ApiException {
		return comicsApi.comicsSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, photonovel, adaptation);
	}

}
