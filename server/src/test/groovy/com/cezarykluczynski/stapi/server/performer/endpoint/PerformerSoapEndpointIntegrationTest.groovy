package com.cezarykluczynski.stapi.server.performer.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_PERFORMERS)
})
class PerformerSoapEndpointIntegrationTest extends AbstractPerformerEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets performer by uid"() {
		when:
		PerformerFullResponse performerResponse = stapiSoapClient.performerPortType.getPerformerFull(new PerformerFullRequest(
				uid: UID
		))

		then:
		performerResponse.performer.uid == UID
	}

	void "gets first page of performers"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 10

		when:
		PerformerBaseResponse performerResponse = stapiSoapClient.performerPortType.getPerformerBase(new PerformerBaseRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize)))

		then:
		performerResponse.page.pageNumber == pageNumber
		performerResponse.page.pageSize == pageSize
		performerResponse.performers.size() == pageSize
	}

	void "gets the only person to star in 6 series"() {
		when:
		PerformerBaseResponse performerResponse = stapiSoapClient.performerPortType.getPerformerBase(new PerformerBaseRequest(
				ds9Performer: true,
				entPerformer: true,
				tasPerformer: true,
				tngPerformer: true,
				tosPerformer: true,
				voyPerformer: true
		))

		then:
		performerResponse.page.totalElements == 1
		performerResponse.performers[0].name == 'Majel Barrett-Roddenberry'
	}

}
