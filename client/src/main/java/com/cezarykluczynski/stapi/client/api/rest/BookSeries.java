package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.BookSeriesSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.BookSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse;

@SuppressWarnings("ParameterNumber")
public class BookSeries {

	private final BookSeriesApi bookSeriesApi;

	public BookSeries(BookSeriesApi bookSeriesApi) {
		this.bookSeriesApi = bookSeriesApi;
	}

	public BookSeriesFullResponse get(String uid) throws ApiException {
		return bookSeriesApi.v1RestBookSeriesGet(uid, null);
	}

	@Deprecated
	public BookSeriesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfBooksFrom, Integer numberOfBooksTo, Integer yearFrom, Integer yearTo, Boolean miniseries,
			@SuppressWarnings("ParameterName") Boolean eBookSeries) throws ApiException {
		return bookSeriesApi.v1RestBookSeriesSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo,
				numberOfBooksFrom, numberOfBooksTo, yearFrom, yearTo, miniseries, eBookSeries);
	}

	public BookSeriesBaseResponse search(BookSeriesSearchCriteria bookSeriesSearchCriteria) throws ApiException {
		return bookSeriesApi.v1RestBookSeriesSearchPost(bookSeriesSearchCriteria.getPageNumber(), bookSeriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(bookSeriesSearchCriteria.getSort()), null, bookSeriesSearchCriteria.getTitle(),
				bookSeriesSearchCriteria.getPublishedYearFrom(), bookSeriesSearchCriteria.getPublishedYearTo(),
				bookSeriesSearchCriteria.getNumberOfBooksFrom(), bookSeriesSearchCriteria.getNumberOfBooksTo(),
				bookSeriesSearchCriteria.getYearFrom(), bookSeriesSearchCriteria.getYearTo(), bookSeriesSearchCriteria.getMiniseries(),
				bookSeriesSearchCriteria.getEBookSeries());
	}


}
