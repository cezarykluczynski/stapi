package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.BookCollectionSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.BookCollectionApi
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse
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
		1 * bookCollectionApiMock.v1RestBookCollectionGet(UID, null) >> bookCollectionFullResponse
		0 * _
		bookCollectionFullResponse == bookCollectionFullResponseOutput
	}

	void "searches entities"() {
		given:
		BookCollectionBaseResponse bookCollectionBaseResponse = Mock()

		when:
		BookCollectionBaseResponse bookCollectionBaseResponseOutput = bookCollection.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE,
				PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO)

		then:
		1 * bookCollectionApiMock.v1RestBookCollectionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO) >>
				bookCollectionBaseResponse
		0 * _
		bookCollectionBaseResponse == bookCollectionBaseResponseOutput
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
		bookCollectionSearchCriteria.sort.addAll(SORT)

		when:
		BookCollectionBaseResponse bookCollectionBaseResponseOutput = bookCollection.search(bookCollectionSearchCriteria)

		then:
		1 * bookCollectionApiMock.v1RestBookCollectionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO) >>
				bookCollectionBaseResponse
		0 * _
		bookCollectionBaseResponse == bookCollectionBaseResponseOutput
	}

}
