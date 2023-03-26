package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.ComicCollectionApi
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest

class ComicCollectionTest extends AbstractComicCollectionTest {

	private ComicCollectionApi comicCollectionApiMock

	private ComicCollection comicCollection

	void setup() {
		comicCollectionApiMock = Mock()
		comicCollection = new ComicCollection(comicCollectionApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		ComicCollectionV2FullResponse comicCollectionV2FullResponse = Mock()

		when:
		ComicCollectionV2FullResponse comicCollectionV2FullResponseOutput = comicCollection.getV2(UID)

		then:
		1 * comicCollectionApiMock.v2GetComicCollection(UID) >> comicCollectionV2FullResponse
		0 * _
		comicCollectionV2FullResponse == comicCollectionV2FullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		ComicCollectionBaseResponse comicCollectionBaseResponse = Mock()
		ComicCollectionSearchCriteria comicCollectionSearchCriteria = new ComicCollectionSearchCriteria(
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
				photonovel: PHOTONOVEL)
		comicCollectionSearchCriteria.sort = SORT

		when:
		ComicCollectionBaseResponse comicCollectionBaseResponseOutput = comicCollection.search(comicCollectionSearchCriteria)

		then:
		1 * comicCollectionApiMock.v1SearchComicCollections(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, PHOTONOVEL) >>
				comicCollectionBaseResponse
		0 * _
		comicCollectionBaseResponse == comicCollectionBaseResponseOutput
	}

}
