package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(JobCompletenessDecider.STEP_002_CREATE_PERFORMERS)
})
class PerformerSoapEndpointIntegrationTest extends AbstractPerformerEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		PerformerResponse performerResponse = stapiSoapClient.performerPortType.getPerformers(new PerformerRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize)))

		then:
		performerResponse.page.pageNumber == pageNumber
		performerResponse.page.pageSize == pageSize
		performerResponse.performers.size() == pageSize
	}

	def "gets the only person to star in 6 series"() {
		when:
		PerformerResponse performerResponse = stapiSoapClient.performerPortType.getPerformers(new PerformerRequest(
				ds9Performer: true,
				entPerformer: true,
				tasPerformer: true,
				tngPerformer: true,
				tosPerformer: true,
				voyPerformer: true
		))

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].name == "Majel Barrett-Roddenberry"
	}

	def "gets performer by guid"() {
		when:
		PerformerResponse performerResponse = stapiSoapClient.performerPortType.getPerformers(new PerformerRequest(
				guid: GUID
		))

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].guid == GUID
	}

}
