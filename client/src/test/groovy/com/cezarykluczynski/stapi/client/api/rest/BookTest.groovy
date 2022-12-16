package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.BookApi
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse
import com.cezarykluczynski.stapi.util.AbstractBookTest

class BookTest extends AbstractBookTest {

	private BookApi bookApiMock

	private Book book

	void setup() {
		bookApiMock = Mock()
		book = new Book(bookApiMock)
	}

	void "gets single entity"() {
		given:
		BookFullResponse bookFullResponse = Mock()

		when:
		BookFullResponse bookFullResponseOutput = book.get(UID)

		then:
		1 * bookApiMock.v1RestBookGet(UID, null) >> bookFullResponse
		0 * _
		bookFullResponse == bookFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		BookV2FullResponse bookV2FullResponse = Mock()

		when:
		BookV2FullResponse bookV2FullResponseOutput = book.getV2(UID)

		then:
		1 * bookApiMock.v2RestBookGet(UID) >> bookV2FullResponse
		0 * _
		bookV2FullResponse == bookV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		BookBaseResponse bookBaseResponse = Mock()

		when:
		BookBaseResponse bookBaseResponseOutput = book.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, NOVEL, REFERENCE_BOOK, BIOGRAPHY_BOOK,
				ROLE_PLAYING_BOOK, E_BOOK, ANTHOLOGY, NOVELIZATION, AUDIOBOOK, AUDIOBOOK_ABRIDGED, AUDIOBOOK_PUBLISHED_YEAR_FROM,
				AUDIOBOOK_PUBLISHED_YEAR_TO, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO)

		then:
		1 * bookApiMock.v1RestBookSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, NOVEL, REFERENCE_BOOK, BIOGRAPHY_BOOK,
				ROLE_PLAYING_BOOK, E_BOOK, ANTHOLOGY, NOVELIZATION, AUDIOBOOK, AUDIOBOOK_ABRIDGED, AUDIOBOOK_PUBLISHED_YEAR_FROM,
				AUDIOBOOK_PUBLISHED_YEAR_TO, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO) >>
				bookBaseResponse
		0 * _
		bookBaseResponse == bookBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		BookV2BaseResponse bookV2BaseResponse = Mock()

		when:
		BookV2BaseResponse bookV2BaseResponseOutput = book.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, NOVEL, REFERENCE_BOOK, BIOGRAPHY_BOOK,
				ROLE_PLAYING_BOOK, E_BOOK, ANTHOLOGY, NOVELIZATION, UNAUTHORIZED_PUBLICATION, AUDIOBOOK, AUDIOBOOK_ABRIDGED,
				AUDIOBOOK_PUBLISHED_YEAR_FROM, AUDIOBOOK_PUBLISHED_YEAR_TO, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO)

		then:
		1 * bookApiMock.v2RestBookSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, NOVEL, REFERENCE_BOOK, BIOGRAPHY_BOOK,
				ROLE_PLAYING_BOOK, E_BOOK, ANTHOLOGY, NOVELIZATION, UNAUTHORIZED_PUBLICATION, AUDIOBOOK, AUDIOBOOK_ABRIDGED,
				AUDIOBOOK_PUBLISHED_YEAR_FROM, AUDIOBOOK_PUBLISHED_YEAR_TO, AUDIOBOOK_RUN_TIME_FROM, AUDIOBOOK_RUN_TIME_TO) >>
				bookV2BaseResponse
		0 * _
		bookV2BaseResponse == bookV2BaseResponseOutput
	}

}
