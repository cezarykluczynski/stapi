package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.BookSeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesSearchCriteria;

public class BookSeries {

	private final BookSeriesApi bookSeriesApi;

	public BookSeries(BookSeriesApi bookSeriesApi) {
		this.bookSeriesApi = bookSeriesApi;
	}

	public BookSeriesFullResponse get(String uid) throws ApiException {
		return bookSeriesApi.v1GetBookSeries(uid);
	}

	public BookSeriesBaseResponse search(BookSeriesSearchCriteria bookSeriesSearchCriteria) throws ApiException {
		return bookSeriesApi.v1SearchBookSeries(bookSeriesSearchCriteria.getPageNumber(), bookSeriesSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(bookSeriesSearchCriteria.getSort()), bookSeriesSearchCriteria.getTitle(),
				bookSeriesSearchCriteria.getPublishedYearFrom(), bookSeriesSearchCriteria.getPublishedYearTo(),
				bookSeriesSearchCriteria.getNumberOfBooksFrom(), bookSeriesSearchCriteria.getNumberOfBooksTo(),
				bookSeriesSearchCriteria.getYearFrom(), bookSeriesSearchCriteria.getYearTo(), bookSeriesSearchCriteria.getMiniseries(),
				bookSeriesSearchCriteria.getEbookSeries());
	}


}
