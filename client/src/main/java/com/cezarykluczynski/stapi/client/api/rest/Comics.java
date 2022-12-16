package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse;

@SuppressWarnings("ParameterNumber")
public class Comics {

	private final ComicsApi comicsApi;

	public Comics(ComicsApi comicsApi) {
		this.comicsApi = comicsApi;
	}

	public ComicsFullResponse get(String uid) throws ApiException {
		return comicsApi.v1RestComicsGet(uid, null);
	}

	public ComicsBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean photonovel, Boolean adaptation) throws ApiException {
		return comicsApi.v1RestComicsSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, photonovel, adaptation);
	}

}
