package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.soap.PerformerRequest
import com.cezarykluczynski.stapi.client.soap.PerformerResponse
import com.cezarykluczynski.stapi.client.soap.RequestPage
import com.cezarykluczynski.stapi.server.series.common.EndpointIntegrationTest
import spock.lang.Ignore

@Ignore
class PerformerSoapEndpointIntegrationTest extends EndpointIntegrationTest {

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
		performerResponse.performers.size() == 10
	}

}
