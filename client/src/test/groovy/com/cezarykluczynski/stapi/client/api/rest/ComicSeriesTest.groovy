package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicSeriesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest

class ComicSeriesTest extends AbstractComicSeriesTest {

	private ComicSeriesApi comicSeriesApiMock

	private ComicSeries comicSeries

	void setup() {
		comicSeriesApiMock = Mock()
		comicSeries = new ComicSeries(comicSeriesApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ComicSeriesFullResponse comicSeriesFullResponse = Mock()

		when:
		ComicSeriesFullResponse comicSeriesFullResponseOutput = comicSeries.get(UID)

		then:
		1 * comicSeriesApiMock.comicSeriesGet(UID, API_KEY) >> comicSeriesFullResponse
		0 * _
		comicSeriesFullResponse == comicSeriesFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicSeriesBaseResponse comicSeriesBaseResponse = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponseOutput = comicSeries.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES,
				PHOTONOVEL_SERIES)

		then:
		1 * comicSeriesApiMock.comicSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES, PHOTONOVEL_SERIES) >>
				comicSeriesBaseResponse
		0 * _
		comicSeriesBaseResponse == comicSeriesBaseResponseOutput
	}

}
