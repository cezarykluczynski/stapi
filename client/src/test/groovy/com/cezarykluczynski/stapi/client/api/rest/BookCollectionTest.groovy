package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.BookCollectionApi
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest

class BookCollectionTest extends AbstractBookCollectionTest {

	private BookCollectionApi bookCollectionApiMock

	private BookCollection bookCollection

	void setup() {
		bookCollectionApiMock = Mock()
		bookCollection = new BookCollection(bookCollectionApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		BookCollectionFullResponse bookCollectionFullResponse = Mock()

		when:
		BookCollectionFullResponse bookCollectionFullResponseOutput = bookCollection.get(UID)

		then:
		1 * bookCollectionApiMock.bookCollectionGet(UID, API_KEY) >> bookCollectionFullResponse
		0 * _
		bookCollectionFullResponse == bookCollectionFullResponseOutput
	}

	void "searches entities"() {
		given:
		BookCollectionBaseResponse bookCollectionBaseResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionBaseResponseOutput = bookCollection.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO)

		then:
		1 * bookCollectionApiMock.bookCollectionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO) >> bookCollectionBaseResponse
		0 * _
		bookCollectionBaseResponse == bookCollectionBaseResponseOutput
	}

}
