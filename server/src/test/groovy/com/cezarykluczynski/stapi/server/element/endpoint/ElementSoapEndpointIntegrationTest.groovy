package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ELEMENTS)
})
class ElementSoapEndpointIntegrationTest extends AbstractElementEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets element by UID"() {
		when:
		ElementFullResponse elementFullResponse = stapiSoapClient.elementPortType.getElementFull(new ElementFullRequest(uid: 'ELMA0000189555'))

		then:
		elementFullResponse.element.name == 'Cerium'
	}

	void "Estonianium is a transuranium element in both Hypersonic series and Transonic series"() {
		when:
		ElementBaseResponse elementFullResponse = stapiSoapClient.elementPortType.getElementBase(new ElementBaseRequest(
				transuranium: true,
				hypersonicSeries: true,
				transonicSeries: true
		))

		then:
		elementFullResponse.elements
				.stream()
				.anyMatch { it.name == 'Estonianium' }
	}

}
