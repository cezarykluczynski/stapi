package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SeriesApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.util.AbstractSeriesTest

class SeriesTest extends AbstractSeriesTest {

	private SeriesApi seriesApiMock

	private Series series

	void setup() {
		seriesApiMock = Mock()
		series = new Series(seriesApiMock)
	}

	void "gets single entity"() {
		given:
		SeriesFullResponse seriesFullResponse = Mock()

		when:
		SeriesFullResponse seriesFullResponseOutput = series.get(UID)

		then:
		1 * seriesApiMock.v1RestSeriesGet(UID, null) >> seriesFullResponse
		0 * _
		seriesFullResponse == seriesFullResponseOutput
	}

	void "searches entities"() {
		given:
		SeriesBaseResponse seriesBaseResponse = Mock()

		when:
		SeriesBaseResponse seriesBaseResponseOutput = series.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, ABBREVIATION, PRODUCTION_START_YEAR_FROM,
				PRODUCTION_START_YEAR_TO, PRODUCTION_END_YEAR_FROM, PRODUCTION_END_YEAR_TO, ORIGINAL_RUN_START_DATE_FROM_DB,
				ORIGINAL_RUN_START_DATE_TO_DB, ORIGINAL_RUN_END_DATE_FROM_DB, ORIGINAL_RUN_END_DATE_TO_DB)

		then:
		1 * seriesApiMock.v1RestSeriesSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, TITLE, ABBREVIATION, PRODUCTION_START_YEAR_FROM,
				PRODUCTION_START_YEAR_TO, PRODUCTION_END_YEAR_FROM, PRODUCTION_END_YEAR_TO, ORIGINAL_RUN_START_DATE_FROM_DB,
				ORIGINAL_RUN_START_DATE_TO_DB, ORIGINAL_RUN_END_DATE_FROM_DB, ORIGINAL_RUN_END_DATE_TO_DB) >> seriesBaseResponse
		0 * _
		seriesBaseResponse == seriesBaseResponseOutput
	}

}
