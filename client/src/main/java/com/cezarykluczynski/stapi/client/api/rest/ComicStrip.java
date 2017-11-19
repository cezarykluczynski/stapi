package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicStripApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicStrip {

	private ComicStripApi comicStripApi;

	private String apiKey;

	public ComicStrip(ComicStripApi comicStripApi, String apiKey) {
		this.comicStripApi = comicStripApi;
		this.apiKey = apiKey;
	}

	public ComicStripFullResponse get(String uid) throws ApiException {
		return comicStripApi.comicStripGet(uid, apiKey);
	}

	public ComicStripBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Integer yearFrom, Integer yearTo) throws ApiException {
		return comicStripApi.comicStripSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, yearFrom, yearTo);
	}

}
