package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.BookSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class BookSeries {

	private final BookSeriesApi bookSeriesApi;

	private final String apiKey;

	public BookSeries(BookSeriesApi bookSeriesApi, String apiKey) {
		this.bookSeriesApi = bookSeriesApi;
		this.apiKey = apiKey;
	}

	public BookSeriesFullResponse get(String uid) throws ApiException {
		return bookSeriesApi.v1RestBookSeriesGet(uid, apiKey);
	}

	public BookSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfBooksFrom, Integer numberOfBooksTo, Integer yearFrom, Integer yearTo, Boolean miniseries,
			@SuppressWarnings("ParameterName") Boolean eBookSeries) throws ApiException {
		return bookSeriesApi.v1RestBookSeriesSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo,
				numberOfBooksFrom, numberOfBooksTo, yearFrom, yearTo, miniseries, eBookSeries);
	}

}
