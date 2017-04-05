package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SERIES)
})
class SeriesRestEndpointIntegrationTest extends AbstractSeriesEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.seriesApi.seriesSearchGet(pageNumber, pageSize)

		then:
		seriesBaseResponse.page.pageNumber == pageNumber
		seriesBaseResponse.page.pageSize == pageSize
		seriesBaseResponse.series.size() == 6
	}

	void "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.seriesApi.seriesSearchPost(pageNumber, pageSize, null, VOYAGER,
				null, null, null, null, null, null, null, null, null)

		then:
		seriesBaseResponse.page.pageNumber == pageNumber
		seriesBaseResponse.page.pageSize == pageSize
		seriesBaseResponse.series.size() == 1
		seriesBaseResponse.series[0].title.contains VOYAGER
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
	})
	void "gets series by guid"() {
		when:
		SeriesFullResponse seriesFullResponse = stapiRestClient.seriesApi.seriesGet(GUID)

		then:
		seriesFullResponse.series.abbreviation == TAS
		seriesFullResponse.series.episodes.size() == 22
	}

	void "gets series sorted by production end year descending"() {
		when:
		SeriesBaseResponse seriesBaseResponse = stapiRestClient.seriesApi.seriesSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'productionEndYear', direction: RestSortDirection.DESC)
				)), null, null, null, null, null, null, null, null, null, null)

		then:
		seriesBaseResponse.series.size() == 6
		seriesBaseResponse.series[0].abbreviation == ENT
		seriesBaseResponse.series[5].abbreviation == TOS
	}

}
