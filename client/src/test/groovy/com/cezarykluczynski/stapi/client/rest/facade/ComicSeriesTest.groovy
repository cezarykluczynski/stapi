package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.ComicSeriesApi
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractComicSeriesTest

class ComicSeriesTest extends AbstractComicSeriesTest {

	private ComicSeriesApi comicSeriesApiMock

	private ComicSeries comicSeries

	void setup() {
		comicSeriesApiMock = Mock()
		comicSeries = new ComicSeries(comicSeriesApiMock)
	}

	void "gets single entity"() {
		given:
		ComicSeriesFullResponse comicSeriesFullResponse = Mock()

		when:
		ComicSeriesFullResponse comicSeriesFullResponseOutput = comicSeries.get(UID)

		then:
		1 * comicSeriesApiMock.v1GetComicSeries(UID) >> comicSeriesFullResponse
		0 * _
		comicSeriesFullResponse == comicSeriesFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		ComicSeriesBaseResponse comicSeriesBaseResponse = Mock()
		ComicSeriesSearchCriteria comicSeriesSearchCriteria = new ComicSeriesSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfIssuesFrom: NUMBER_OF_ISSUES_FROM,
				numberOfIssuesTo: NUMBER_OF_ISSUES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_FROM,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES)
		comicSeriesSearchCriteria.sort = SORT

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponseOutput = comicSeries.search(comicSeriesSearchCriteria)

		then:
		1 * comicSeriesApiMock.v1SearchComicSeries(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES, PHOTONOVEL_SERIES) >>
				comicSeriesBaseResponse
		0 * _
		comicSeriesBaseResponse == comicSeriesBaseResponseOutput
	}

}
