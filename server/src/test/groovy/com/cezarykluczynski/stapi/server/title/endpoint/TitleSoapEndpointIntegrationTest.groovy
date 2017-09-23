package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TITLES)
})
class TitleSoapEndpointIntegrationTest extends AbstractTitleEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets title by UID"() {
		when:
		TitleFullResponse titleFullResponse = stapiSoapClient.titlePortType.getTitleFull(new TitleFullRequest(
				uid: 'TIMA0000005009'
		))

		then:
		titleFullResponse.title.name == 'Constable'
	}

	void "Subaltern can by found by name"() {
		when:
		TitleBaseResponse titleBaseResponse = stapiSoapClient.titlePortType.getTitleBase(new TitleBaseRequest(
				name: 'Subaltern'
		))

		then:
		titleBaseResponse.titles
				.stream()
				.anyMatch { it -> it.name == 'Subaltern' }
	}

}
