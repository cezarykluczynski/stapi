package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.BookCollectionApi
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractBookCollectionTest

class BookCollectionTest extends AbstractBookCollectionTest {

	private BookCollectionApi bookCollectionApiMock

	private BookCollection bookCollection

	void setup() {
		bookCollectionApiMock = Mock()
		bookCollection = new BookCollection(bookCollectionApiMock)
	}

	void "gets single entity"() {
		given:
		BookCollectionFullResponse bookCollectionFullResponse = Mock()

		when:
		BookCollectionFullResponse bookCollectionFullResponseOutput = bookCollection.get(UID)

		then:
		1 * bookCollectionApiMock.v1GetBookCollection(UID) >> bookCollectionFullResponse
		0 * _
		bookCollectionFullResponse == bookCollectionFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		BookCollectionBaseResponse bookCollectionBaseResponse = Mock()
		BookCollectionSearchCriteria bookCollectionSearchCriteria = new BookCollectionSearchCriteria(
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
				yearTo: YEAR_TO)
		bookCollectionSearchCriteria.sort = SORT

		when:
		BookCollectionBaseResponse bookCollectionBaseResponseOutput = bookCollection.search(bookCollectionSearchCriteria)

		then:
		1 * bookCollectionApiMock.v1SearchBookCollections(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO) >>
				bookCollectionBaseResponse
		0 * _
		bookCollectionBaseResponse == bookCollectionBaseResponseOutput
	}

}
