package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.BookCollectionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse;

@SuppressWarnings("ParameterNumber")
public class BookCollection {

	private final BookCollectionApi bookCollectionApi;

	private final String apiKey;

	public BookCollection(BookCollectionApi bookCollectionApi, String apiKey) {
		this.bookCollectionApi = bookCollectionApi;
		this.apiKey = apiKey;
	}

	public BookCollectionFullResponse get(String uid) throws ApiException {
		return bookCollectionApi.bookCollectionGet(uid, apiKey);
	}

	public BookCollectionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo) throws ApiException {
		return bookCollectionApi.bookCollectionSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo,
				numberOfPagesFrom, numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo);
	}

}
