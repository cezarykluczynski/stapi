package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SeriesApi
import com.cezarykluczynski.stapi.client.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.client.rest.model.SeriesSearchCriteria
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
		1 * seriesApiMock.v1GetSeries(UID) >> seriesFullResponse
		0 * _
		seriesFullResponse == seriesFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		SeriesBaseResponse seriesBaseResponse = Mock()
		SeriesSearchCriteria seriesSearchCriteria = new SeriesSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYearFrom: PRODUCTION_START_YEAR_FROM,
				productionStartYearTo: PRODUCTION_START_YEAR_TO,
				productionEndYearFrom: PRODUCTION_END_YEAR_FROM,
				productionEndYearTo: PRODUCTION_END_YEAR_TO,
				originalRunStartDateFrom: ORIGINAL_RUN_START_DATE_FROM_DB,
				originalRunStartDateTo: ORIGINAL_RUN_START_DATE_TO_DB,
				originalRunEndDateFrom: ORIGINAL_RUN_END_DATE_FROM_DB,
				originalRunEndDateTo: ORIGINAL_RUN_END_DATE_TO_DB)
		seriesSearchCriteria.sort = SORT

		when:
		SeriesBaseResponse seriesBaseResponseOutput = series.search(seriesSearchCriteria)

		then:
		1 * seriesApiMock.v1SearchSeries(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, ABBREVIATION, PRODUCTION_START_YEAR_FROM,
				PRODUCTION_START_YEAR_TO, PRODUCTION_END_YEAR_FROM, PRODUCTION_END_YEAR_TO, ORIGINAL_RUN_START_DATE_FROM_DB,
				ORIGINAL_RUN_START_DATE_TO_DB, ORIGINAL_RUN_END_DATE_FROM_DB, ORIGINAL_RUN_END_DATE_TO_DB) >> seriesBaseResponse
		0 * _
		seriesBaseResponse == seriesBaseResponseOutput
	}

}
