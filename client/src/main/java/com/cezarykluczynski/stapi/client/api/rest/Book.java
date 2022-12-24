package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.BookV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Book {

	private final BookApi bookApi;

	public Book(BookApi bookApi) {
		this.bookApi = bookApi;
	}

	@Deprecated
	public BookFullResponse get(String uid) throws ApiException {
		return bookApi.v1RestBookGet(uid, null);
	}

	public BookV2FullResponse getV2(String uid) throws ApiException {
		return bookApi.v2RestBookGet(uid);
	}

	@SuppressWarnings("ParameterName")
	@Deprecated
	public BookBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean novel, Boolean referenceBook, Boolean biographyBook, Boolean rolePlayingBook, Boolean eBook, Boolean anthology,
			Boolean novelization, Boolean audiobook, Boolean audiobookAbridged, Integer audiobookPublishedYearFrom, Integer audiobookPublishedYearTo,
			Integer audiobookRunTimeFrom, Integer audiobookRunTimeTo) throws ApiException {
		return bookApi.v1RestBookSearchPost(pageNumber, pageSize, sort, null, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, novel, referenceBook, biographyBook, rolePlayingBook, eBook, anthology,
				novelization, audiobook, audiobookAbridged, audiobookPublishedYearFrom, audiobookPublishedYearTo, audiobookRunTimeFrom,
				audiobookRunTimeTo);
	}

	@SuppressWarnings("ParameterName")
	@Deprecated
	public BookV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean novel, Boolean referenceBook, Boolean biographyBook, Boolean rolePlayingBook, Boolean eBook, Boolean anthology,
			Boolean novelization, Boolean unauthorizedPublication, Boolean audiobook, Boolean audiobookAbridged, Integer audiobookPublishedYearFrom,
			Integer audiobookPublishedYearTo, Integer audiobookRunTimeFrom, Integer audiobookRunTimeTo) throws ApiException {
		return bookApi.v2RestBookSearchPost(pageNumber, pageSize, sort, title, publishedYearFrom, publishedYearTo, numberOfPagesFrom,
				numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, novel, referenceBook, biographyBook, rolePlayingBook, eBook, anthology,
				novelization, unauthorizedPublication, audiobook, audiobookAbridged, audiobookPublishedYearFrom, audiobookPublishedYearTo,
				audiobookRunTimeFrom, audiobookRunTimeTo);
	}

	@SuppressWarnings("ParameterName")
	public BookV2BaseResponse searchV2(BookV2SearchCriteria bookV2SearchCriteria) throws ApiException {
		return bookApi.v2RestBookSearchPost(bookV2SearchCriteria.getPageNumber(), bookV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(bookV2SearchCriteria.getSort()), bookV2SearchCriteria.getTitle(),
				bookV2SearchCriteria.getPublishedYearFrom(), bookV2SearchCriteria.getPublishedYearTo(), bookV2SearchCriteria.getNumberOfPagesFrom(),
				bookV2SearchCriteria.getNumberOfPagesTo(), bookV2SearchCriteria.getStardateFrom(), bookV2SearchCriteria.getStardateTo(),
				bookV2SearchCriteria.getYearFrom(), bookV2SearchCriteria.getYearTo(), bookV2SearchCriteria.getNovel(),
				bookV2SearchCriteria.getReferenceBook(), bookV2SearchCriteria.getBiographyBook(), bookV2SearchCriteria.getRolePlayingBook(),
				bookV2SearchCriteria.getEBook(), bookV2SearchCriteria.getAnthology(), bookV2SearchCriteria.getNovelization(),
				bookV2SearchCriteria.getUnauthorizedPublication(), bookV2SearchCriteria.getAudiobook(), bookV2SearchCriteria.getAudiobookAbridged(),
				bookV2SearchCriteria.getAudiobookPublishedYearFrom(), bookV2SearchCriteria.getAudiobookPublishedYearTo(),
				bookV2SearchCriteria.getAudiobookRunTimeFrom(), bookV2SearchCriteria.getAudiobookRunTimeTo());
	}

}
