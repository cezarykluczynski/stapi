package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicCollectionApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest

class ComicCollectionTest extends AbstractComicCollectionTest {

	private ComicCollectionApi comicCollectionApiMock

	private ComicCollection comicCollection

	void setup() {
		comicCollectionApiMock = Mock()
		comicCollection = new ComicCollection(comicCollectionApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ComicCollectionFullResponse comicCollectionFullResponse = Mock()

		when:
		ComicCollectionFullResponse comicCollectionFullResponseOutput = comicCollection.get(UID)

		then:
		1 * comicCollectionApiMock.comicCollectionGet(UID, API_KEY) >> comicCollectionFullResponse
		0 * _
		comicCollectionFullResponse == comicCollectionFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicCollectionBaseResponse comicCollectionBaseResponse = Mock()

		when:
		ComicCollectionBaseResponse comicCollectionBaseResponseOutput = comicCollection.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE,
				PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				PHOTONOVEL)

		then:
		1 * comicCollectionApiMock.comicCollectionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, PHOTONOVEL) >> comicCollectionBaseResponse
		0 * _
		comicCollectionBaseResponse == comicCollectionBaseResponseOutput
	}

}
