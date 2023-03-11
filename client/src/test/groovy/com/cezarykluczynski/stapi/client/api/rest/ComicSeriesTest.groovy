package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.ComicSeriesSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicSeriesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse
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
		1 * comicSeriesApiMock.v1RestComicSeriesGet(UID) >> comicSeriesFullResponse
		0 * _
		comicSeriesFullResponse == comicSeriesFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicSeriesBaseResponse comicSeriesBaseResponse = Mock()

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponseOutput = comicSeries.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES,
				PHOTONOVEL_SERIES)

		then:
		1 * comicSeriesApiMock.v1RestComicSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES, PHOTONOVEL_SERIES) >>
				comicSeriesBaseResponse
		0 * _
		comicSeriesBaseResponse == comicSeriesBaseResponseOutput
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
		comicSeriesSearchCriteria.sort.addAll(SORT)

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponseOutput = comicSeries.search(comicSeriesSearchCriteria)

		then:
		1 * comicSeriesApiMock.v1RestComicSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO, STARDATE_FROM, STARDATE_FROM, YEAR_FROM, YEAR_TO, MINISERIES, PHOTONOVEL_SERIES) >>
				comicSeriesBaseResponse
		0 * _
		comicSeriesBaseResponse == comicSeriesBaseResponseOutput
	}

}
