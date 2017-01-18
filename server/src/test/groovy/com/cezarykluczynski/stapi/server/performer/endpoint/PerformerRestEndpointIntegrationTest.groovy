package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_PERFORMERS)
})
class PerformerRestEndpointIntegrationTest extends AbstractPerformerEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		PerformerResponse performerResponse = stapiRestClient.performerApi.performerGet(pageNumber, pageSize)

		then:
		performerResponse.page.pageNumber == pageNumber
		performerResponse.page.pageSize == pageSize
		performerResponse.performers.size() == pageSize
	}

	def "gets the only person to star in 6 series"() {
		when:
		PerformerResponse performerResponse = stapiRestClient.performerApi.performerPost(null, null, null, null, null,
				null, null, null, null, null, null, null, null, true, true, null, null, null, true, true, true, null,
				null, true)

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].name == "Majel Barrett-Roddenberry"
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES) &&
				StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MOVIES)
	})
	def "gets performer by guid"() {
		when:
		PerformerResponse performerResponse = stapiRestClient.performerApi.performerPost(null, null, null, GUID, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null)

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].guid == GUID
		performerResponse.performers[0].episodesPerformanceHeaders.size() == 177
		performerResponse.performers[0].moviesPerformanceHeaders.size() == 4
	}

	def "gets performers sorted by name"() {
		when:
		PerformerResponse performerResponse = stapiRestClient.performerApi.performerPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'name', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null)

		then:
		performerResponse.performers[0].name.startsWith("A. ")
		performerResponse.performers[1].name.startsWith("A. ")
	}

}
