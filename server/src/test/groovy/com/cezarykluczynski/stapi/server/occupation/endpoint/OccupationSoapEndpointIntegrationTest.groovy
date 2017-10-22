package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_OCCUPATIONS)
})
class OccupationSoapEndpointIntegrationTest extends AbstractOccupationEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets occupation by UID"() {
		when:
		OccupationFullResponse occupationFullResponse = stapiSoapClient.occupationPortType
				.getOccupationFull(new OccupationFullRequest(uid: 'OCMA0000021171'))

		then:
		occupationFullResponse.occupation.name == 'Conservator'
	}

	void "Cowboy can be found by name"() {
		when:
		OccupationBaseResponse occupationFullResponse = stapiSoapClient.occupationPortType.getOccupationBase(new OccupationBaseRequest(
				name: 'Cowboy'
		))

		then:
		occupationFullResponse.occupations
				.stream()
				.anyMatch { it.name == 'Cowboy' }
	}

}
