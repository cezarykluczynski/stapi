package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.SeriesSearchCriteria
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SERIES)
})
class SeriesRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	private static final String VOYAGER = 'Voyager'

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
	})
	void "gets series by UID"() {
		when:
		SeriesFullResponse seriesFullResponse = stapiRestClient.series.get('SEMA0000034504')

		then:
		seriesFullResponse.series.abbreviation == 'TAS'
		seriesFullResponse.series.episodes.size() == 22
	}

	void "gets all series"() {
		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.series.search(new SeriesSearchCriteria())

		then:
		seriesBaseResponse.page.pageNumber == 0
		seriesBaseResponse.page.pageSize == 50
		seriesBaseResponse.series.size() == 11
	}

	void "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2
		SeriesSearchCriteria seriesSearchCriteria = new SeriesSearchCriteria(
				title: VOYAGER,
				pageNumber: pageNumber,
				pageSize: pageSize
		)

		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.series.search(seriesSearchCriteria)

		then:
		seriesBaseResponse.page.pageNumber == pageNumber
		seriesBaseResponse.page.pageSize == pageSize
		seriesBaseResponse.series.size() == 1
		seriesBaseResponse.series[0].title.contains VOYAGER
	}

	void "gets series sorted by production end year descending"() {
		given:
		SeriesSearchCriteria seriesSearchCriteria = new SeriesSearchCriteria()
		seriesSearchCriteria.sort.add(new RestSortClause(name: 'originalRunStartDate', direction: RestSortDirection.DESC))

		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.series.search(seriesSearchCriteria)

		then:
		seriesBaseResponse.series.size() == 11
		seriesBaseResponse.series.first().abbreviation == 'SNW'
		seriesBaseResponse.series.last().abbreviation == 'TOS'
	}

}
