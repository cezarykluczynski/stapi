package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.BookV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.BookV2SearchCriteria;

public class Book {

	private final BookApi bookApi;

	public Book(BookApi bookApi) {
		this.bookApi = bookApi;
	}

	public BookV2FullResponse getV2(String uid) throws ApiException {
		return bookApi.v2GetBook(uid);
	}

	public BookV2BaseResponse searchV2(BookV2SearchCriteria bookV2SearchCriteria) throws ApiException {
		return bookApi.v2SearchBooks(bookV2SearchCriteria.getPageNumber(), bookV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(bookV2SearchCriteria.getSort()), bookV2SearchCriteria.getTitle(),
				bookV2SearchCriteria.getPublishedYearFrom(), bookV2SearchCriteria.getPublishedYearTo(), bookV2SearchCriteria.getNumberOfPagesFrom(),
				bookV2SearchCriteria.getNumberOfPagesTo(), bookV2SearchCriteria.getStardateFrom(), bookV2SearchCriteria.getStardateTo(),
				bookV2SearchCriteria.getYearFrom(), bookV2SearchCriteria.getYearTo(), bookV2SearchCriteria.getNovel(),
				bookV2SearchCriteria.getReferenceBook(), bookV2SearchCriteria.getBiographyBook(), bookV2SearchCriteria.getRolePlayingBook(),
				bookV2SearchCriteria.getEbook(), bookV2SearchCriteria.getAnthology(), bookV2SearchCriteria.getNovelization(),
				bookV2SearchCriteria.getUnauthorizedPublication(), bookV2SearchCriteria.getAudiobook(), bookV2SearchCriteria.getAudiobookAbridged(),
				bookV2SearchCriteria.getAudiobookPublishedYearFrom(), bookV2SearchCriteria.getAudiobookPublishedYearTo(),
				bookV2SearchCriteria.getAudiobookRunTimeFrom(), bookV2SearchCriteria.getAudiobookRunTimeTo());
	}

}
