package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicCollectionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicCollection {

	private final ComicCollectionApi comicCollectionApi;

	private final String apiKey;

	public ComicCollection(ComicCollectionApi comicCollectionApi, String apiKey) {
		this.comicCollectionApi = comicCollectionApi;
		this.apiKey = apiKey;
	}

	public ComicCollectionFullResponse get(String uid) throws ApiException {
		return comicCollectionApi.comicCollectionGet(uid, apiKey);
	}

	public ComicCollectionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean photonovel) throws ApiException {
		return comicCollectionApi.comicCollectionSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo,
				numberOfPagesFrom, numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, photonovel);
	}

}
