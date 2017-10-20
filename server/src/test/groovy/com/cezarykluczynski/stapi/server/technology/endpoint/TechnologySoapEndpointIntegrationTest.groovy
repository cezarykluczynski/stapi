package com.cezarykluczynski.stapi.server.technology.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TECHNOLOGY)
})
class TechnologySoapEndpointIntegrationTest extends AbstractTechnologyEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets technology by UID"() {
		when:
		TechnologyFullResponse technologyFullResponse = stapiSoapClient.technologyPortType
				.getTechnologyFull(new TechnologyFullRequest(uid: 'TEMA0000200906'))

		then:
		technologyFullResponse.technology.name == 'Autoclave'
	}

	void "Neural transceiver is among Borg communication technologies"() {
		when:
		TechnologyBaseResponse technologyFullResponse = stapiSoapClient.technologyPortType.getTechnologyBase(new TechnologyBaseRequest(
				borgTechnology: true,
				communicationsTechnology: true
		))

		then:
		technologyFullResponse.technology
				.stream()
				.anyMatch { it.name == 'Neural transceiver' }
	}

}
