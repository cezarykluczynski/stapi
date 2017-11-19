package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicStripApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.util.AbstractComicStripTest

class ComicStripTest extends AbstractComicStripTest {

	private ComicStripApi comicStripApiMock

	private ComicStrip comicStrip

	void setup() {
		comicStripApiMock = Mock()
		comicStrip = new ComicStrip(comicStripApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ComicStripFullResponse comicStripFullResponse = Mock()

		when:
		ComicStripFullResponse comicStripFullResponseOutput = comicStrip.get(UID)

		then:
		1 * comicStripApiMock.comicStripGet(UID, API_KEY) >> comicStripFullResponse
		0 * _
		comicStripFullResponse == comicStripFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicStripBaseResponse comicStripBaseResponse = Mock()

		when:
		ComicStripBaseResponse comicStripBaseResponseOutput = comicStrip.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM,
		PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, YEAR_FROM, YEAR_TO)

		then:
		1 * comicStripApiMock.comicStripSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, YEAR_FROM, YEAR_TO) >> comicStripBaseResponse
		0 * _
		comicStripBaseResponse == comicStripBaseResponseOutput
	}

}
