package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.BookApi
import com.cezarykluczynski.stapi.client.rest.model.BookV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.BookV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractBookTest

class BookTest extends AbstractBookTest {

	private BookApi bookApiMock

	private Book book

	void setup() {
		bookApiMock = Mock()
		book = new Book(bookApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		BookV2FullResponse bookV2FullResponse = Mock()

		when:
		BookV2FullResponse bookV2FullResponseOutput = book.getV2(UID)

		then:
		1 * bookApiMock.v2GetBook(UID) >> bookV2FullResponse
		0 * _
		bookV2FullResponse == bookV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		BookV2BaseResponse bookV2BaseResponse = Mock()
		BookV2SearchCriteria bookV2SearchCriteria = new BookV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				novel: NOVEL,
				referenceBook: REFERENCE_BOOK,
				biographyBook: BIOGRAPHY_BOOK,
				rolePlayingBook: ROLE_PLAYING_BOOK,
				ebook: E_BOOK,
				anthology: ANTHOLOGY,
				novelization: NOVELIZATION,
				unauthorizedPublication: UNAUTHORIZED_PUBLICATION,
				audiobook: AUDIOBOOK,
				audiobookAbridged: AUDIOBOOK_ABRIDGED,
				audiobookPublishedYearFrom: AUDIOBOOK_PUBLISHED_YEAR_FROM,
				audiobookPublishedYearTo: AUDIOBOOK_PUBLISHED_YEAR_TO,
				audiobookRunTimeFrom: AUDIOBOOK_RUN_TIME_FROM,
				audiobookRunTimeTo: AUDIOBOOK_RUN_TIME_TO)
		bookV2SearchCriteria.sort = SORT

		when:
		BookV2BaseResponse bookV2BaseResponseOutput = book.searchV2(bookV2SearchCriteria)

		then:
		1 * bookApiMock.v2SearchBooks(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, NOVEL, REFERENCE_BOOK, BIOGRAPHY_BOOK,
				ROLE_PLAYING_BOOK, E_BOOK, ANTHOLOGY, NOVELIZATION, UNAUTHORIZED_PUBLICATION, AUDIOBOOK, AUDIOBOOK_ABRIDGED,
				AUDIOBOOK_PUBLISHED_YEAR_FROM, AUDIOBOOK_PUBLISHED_YEAR_TO, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO) >>
				bookV2BaseResponse
		0 * _
		bookV2BaseResponse == bookV2BaseResponseOutput
	}

}
