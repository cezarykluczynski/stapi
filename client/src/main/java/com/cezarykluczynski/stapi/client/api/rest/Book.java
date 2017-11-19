package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;

@SuppressWarnings("ParameterNumber")
public class Book {

	private final BookApi bookApi;

	private final String apiKey;

	public Book(BookApi bookApi, String apiKey) {
		this.bookApi = bookApi;
		this.apiKey = apiKey;
	}

	public BookFullResponse get(String uid) throws ApiException {
		return bookApi.bookGet(uid, apiKey);
	}

	@SuppressWarnings("ParameterName")
	public BookBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean novel, Boolean referenceBook, Boolean biographyBook, Boolean rolePlayingBook, Boolean eBook, Boolean anthology,
			Boolean novelization, Boolean audiobook, Boolean audiobookAbridged, Integer audiobookPublishedYearFrom, Integer audiobookPublishedYearTo,
			Integer audiobookRunTimeFrom, Integer audiobookRunTimeTo) throws ApiException {
		return bookApi.bookSearchPost(pageNumber, pageSize, sort, apiKey, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, novel, referenceBook, biographyBook, rolePlayingBook, eBook, anthology,
				novelization, audiobook, audiobookAbridged, audiobookPublishedYearFrom, audiobookPublishedYearTo, audiobookRunTimeFrom,
				audiobookRunTimeTo);
	}

}
