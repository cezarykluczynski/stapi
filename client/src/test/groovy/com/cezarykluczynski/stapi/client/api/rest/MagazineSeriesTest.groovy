package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineSeriesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse
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
		1 * magazineSeriesApiMock.v1RestMagazineSeriesGet(UID, null) >> magazineSeriesFullResponse
		0 * _
		magazineSeriesFullResponse == magazineSeriesFullResponseOutput
	}

	void "searches entities"() {
		given:
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesBaseResponseOutput = magazineSeries.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM,
				PUBLISHED_YEAR_TO, NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO)

		then:
		1 * magazineSeriesApiMock.v1RestMagazineSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_ISSUES_FROM, NUMBER_OF_ISSUES_TO) >> magazineSeriesBaseResponse
		0 * _
		magazineSeriesBaseResponse == magazineSeriesBaseResponseOutput
	}

}
