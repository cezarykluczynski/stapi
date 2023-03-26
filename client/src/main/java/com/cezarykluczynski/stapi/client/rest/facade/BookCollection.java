package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.BookCollectionApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionSearchCriteria;

public class BookCollection {

	private final BookCollectionApi bookCollectionApi;

	public BookCollection(BookCollectionApi bookCollectionApi) {
		this.bookCollectionApi = bookCollectionApi;
	}

	public BookCollectionFullResponse get(String uid) throws ApiException {
		return bookCollectionApi.v1GetBookCollection(uid);
	}

	public BookCollectionBaseResponse search(BookCollectionSearchCriteria bookCollectionSearchCriteria) throws ApiException {
		return bookCollectionApi.v1SearchBookCollections(bookCollectionSearchCriteria.getPageNumber(),
				bookCollectionSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(bookCollectionSearchCriteria.getSort()),
				bookCollectionSearchCriteria.getTitle(), bookCollectionSearchCriteria.getPublishedYearFrom(),
				bookCollectionSearchCriteria.getPublishedYearTo(), bookCollectionSearchCriteria.getNumberOfPagesFrom(),
				bookCollectionSearchCriteria.getNumberOfPagesTo(), bookCollectionSearchCriteria.getStardateFrom(),
				bookCollectionSearchCriteria.getStardateTo(), bookCollectionSearchCriteria.getYearFrom(), bookCollectionSearchCriteria.getYearTo());
	}

}
