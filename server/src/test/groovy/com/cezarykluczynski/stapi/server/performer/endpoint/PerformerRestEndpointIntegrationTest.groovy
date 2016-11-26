package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse
import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(JobCompletenessDecider.STEP_002_CREATE_PERFORMERS)
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
				null, null, null, null, null, null, null, true, true, null, null, null, true, true, true, null, null,
				true)

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].name == "Majel Barrett-Roddenberry"
	}

	def "gets performer by guid"() {
		when:
		PerformerResponse performerResponse = stapiRestClient.performerApi.performerPost(null, null, GUID, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null)

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].guid == GUID
	}

}
