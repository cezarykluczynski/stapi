package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.BookCollectionSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.BookCollectionApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionFullResponse;

@SuppressWarnings("ParameterNumber")
public class BookCollection {

	private final BookCollectionApi bookCollectionApi;

	public BookCollection(BookCollectionApi bookCollectionApi) {
		this.bookCollectionApi = bookCollectionApi;
	}

	public BookCollectionFullResponse get(String uid) throws ApiException {
		return bookCollectionApi.v1RestBookCollectionGet(uid);
	}

	@Deprecated
	public BookCollectionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo) throws ApiException {
		return bookCollectionApi.v1RestBookCollectionSearchPost(pageNumber, pageSize, sort, title, publishedYearFrom, publishedYearTo,
				numberOfPagesFrom, numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo);
	}

	public BookCollectionBaseResponse search(BookCollectionSearchCriteria bookCollectionSearchCriteria) throws ApiException {
		return bookCollectionApi.v1RestBookCollectionSearchPost(bookCollectionSearchCriteria.getPageNumber(),
				bookCollectionSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(bookCollectionSearchCriteria.getSort()),
				bookCollectionSearchCriteria.getTitle(), bookCollectionSearchCriteria.getPublishedYearFrom(),
				bookCollectionSearchCriteria.getPublishedYearTo(), bookCollectionSearchCriteria.getNumberOfPagesFrom(),
				bookCollectionSearchCriteria.getNumberOfPagesTo(), bookCollectionSearchCriteria.getStardateFrom(),
				bookCollectionSearchCriteria.getStardateTo(), bookCollectionSearchCriteria.getYearFrom(), bookCollectionSearchCriteria.getYearTo());
	}

}
