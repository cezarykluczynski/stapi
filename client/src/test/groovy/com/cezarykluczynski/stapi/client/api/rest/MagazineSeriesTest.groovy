package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.MagazineSeriesApi
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.client.rest.model.MagazineSeriesSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractMagazineSeriesTest

class MagazineSeriesTest extends AbstractMagazineSeriesTest {

	private MagazineSeriesApi magazineSeriesApiMock

	private MagazineSeries magazineSeries

	void setup() {
		magazineSeriesApiMock = Mock()
		magazineSeries = new MagazineSeries(magazineSeriesApiMock)
	}

	void "gets single entity"() {
		given:
		MagazineSeriesFullResponse magazineSeriesFullResponse = Mock()

		when:
		MagazineSeriesFullResponse magazineSeriesFullResponseOutput = magazineSeries.get(UID)

		then:
		1 * magazineSeriesApiMock.v1GetMagazineSeries(UID) >> magazineSeriesFullResponse
		0 * _
		magazineSeriesFullResponse == magazineSeriesFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()
		MagazineSeriesSearchCriteria magazineSeriesSearchCriteria = new MagazineSeriesSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfIssuesFrom: NUMBER_OF_ISSUES_FROM,
				numberOfIssuesTo: NUMBER_OF_ISSUES_TO)
		magazineSeriesSearchCriteria.sort = SORT

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponseOutput = magazineSeries.search(magazineSeriesSearchCriteria)

		then:
		1 * magazineSeriesApiMock.v1SearchMagazineSeries(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO) >> magazineSeriesBaseResponse
		0 * _
		magazineSeriesBaseResponse == magazineSeriesBaseResponseOutput
	}

}
