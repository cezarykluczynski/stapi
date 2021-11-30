package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Book {

	private final BookApi bookApi;

	private final String apiKey;

	public Book(BookApi bookApi, String apiKey) {
		this.bookApi = bookApi;
		this.apiKey = apiKey;
	}

	@Deprecated
	public BookFullResponse get(String uid) throws ApiException {
		return bookApi.v1RestBookGet(uid, apiKey);
	}

	public BookV2FullResponse getV2(String uid) throws ApiException {
		return bookApi.v2RestBookGet(uid, apiKey);
	}

	@SuppressWarnings("ParameterName")
	@Deprecated
	public BookBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean novel, Boolean referenceBook, Boolean biographyBook, Boolean rolePlayingBook, Boolean eBook, Boolean anthology,
			Boolean novelization, Boolean audiobook, Boolean audiobookAbridged, Integer audiobookPublishedYearFrom, Integer audiobookPublishedYearTo,
			Integer audiobookRunTimeFrom, Integer audiobookRunTimeTo) throws ApiException {
		return bookApi.v1RestBookSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, novel, referenceBook, biographyBook, rolePlayingBook, eBook, anthology,
				novelization, audiobook, audiobookAbridged, audiobookPublishedYearFrom, audiobookPublishedYearTo, audiobookRunTimeFrom,
				audiobookRunTimeTo);
	}

	@SuppressWarnings("ParameterName")
	public BookV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean novel, Boolean referenceBook, Boolean biographyBook, Boolean rolePlayingBook, Boolean eBook, Boolean anthology,
			Boolean novelization, Boolean unauthorizedPublication, Boolean audiobook, Boolean audiobookAbridged, Integer audiobookPublishedYearFrom,
			Integer audiobookPublishedYearTo, Integer audiobookRunTimeFrom, Integer audiobookRunTimeTo) throws ApiException {
		return bookApi.v2RestBookSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, novel, referenceBook, biographyBook, rolePlayingBook, eBook, anthology,
				novelization, unauthorizedPublication, audiobook, audiobookAbridged, audiobookPublishedYearFrom, audiobookPublishedYearTo,
				audiobookRunTimeFrom, audiobookRunTimeTo);
	}

}
