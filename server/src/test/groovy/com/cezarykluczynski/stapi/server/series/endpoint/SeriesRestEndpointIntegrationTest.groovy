package com.cezarykluczynski.stapi.server.series.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SERIES) &&
			StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
})
class SeriesRestEndpointIntegrationTest extends AbstractSeriesEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets all series"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesGet(pageNumber, pageSize)

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 6
	}

	def "gets series by title"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(pageNumber, pageSize, null, null, VOYAGER,
				null, null, null, null, null, null, null, null, null)

		then:
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
		seriesResponse.series.size() == 1
		seriesResponse.series[0].title.contains VOYAGER
	}

	def "gets series by guid"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 2

		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(pageNumber, pageSize, null, GUID, null,
				null, null, null, null, null, null, null, null, null)

		then:
		seriesResponse.series.size() == 1
		seriesResponse.series[0].abbreviation == TAS
		seriesResponse.series[0].episodeHeaders.size() == 22
		seriesResponse.page.pageNumber == pageNumber
		seriesResponse.page.pageSize == pageSize
	}

	def "gets series sorted by production end year descending"() {
		when:
		SeriesResponse seriesResponse = stapiRestClient.seriesApi.seriesPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'productionEndYear', direction: RestSortDirection.DESC)
				)), null, null, null, null, null, null, null, null, null, null, null)

		then:
		seriesResponse.series.size() == 6
		seriesResponse.series[0].abbreviation == ENT
		seriesResponse.series[5].abbreviation == TOS
	}

}
