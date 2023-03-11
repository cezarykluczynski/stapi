package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.ComicStripSearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.ComicStripApi
import com.cezarykluczynski.stapi.client.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.util.AbstractComicStripTest

class ComicStripTest extends AbstractComicStripTest {

	private ComicStripApi comicStripApiMock

	private ComicStrip comicStrip

	void setup() {
		comicStripApiMock = Mock()
		comicStrip = new ComicStrip(comicStripApiMock)
	}

	void "gets single entity"() {
		given:
		ComicStripFullResponse comicStripFullResponse = Mock()

		when:
		ComicStripFullResponse comicStripFullResponseOutput = comicStrip.get(UID)

		then:
		1 * comicStripApiMock.v1RestComicStripGet(UID) >> comicStripFullResponse
		0 * _
		comicStripFullResponse == comicStripFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicStripBaseResponse comicStripBaseResponse = Mock()

		when:
		ComicStripBaseResponse comicStripBaseResponseOutput = comicStrip.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM,
		PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, YEAR_FROM, YEAR_TO)

		then:
		1 * comicStripApiMock.v1RestComicStripSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, YEAR_FROM, YEAR_TO) >> comicStripBaseResponse
		0 * _
		comicStripBaseResponse == comicStripBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		ComicStripBaseResponse comicStripBaseResponse = Mock()
		ComicStripSearchCriteria comicStripSearchCriteria = new ComicStripSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)
		comicStripSearchCriteria.sort.addAll(SORT)

		when:
		ComicStripBaseResponse comicStripBaseResponseOutput = comicStrip.search(comicStripSearchCriteria)

		then:
		1 * comicStripApiMock.v1RestComicStripSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, YEAR_FROM, YEAR_TO) >> comicStripBaseResponse
		0 * _
		comicStripBaseResponse == comicStripBaseResponseOutput
	}

}
