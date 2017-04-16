package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ASTRONOMICAL_OBJECTS) &&
			StaticJobCompletenessDecider.isStepCompleted(StepName.LINK_ASTRONOMICAL_OBJECTS)
})
class AstronomicalObjectRestEndpointIntegrationTest extends AbstractAstronomicalObjectEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "finds Andoria by astronomical object type"() {
		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponse = stapiRestClient.astronomicalObjectApi.astronomicalObjectSearchPost(0, 20, null,
				null, null, 'M_CLASS_MOON', null)

		then:
		astronomicalObjectResponse.astronomicalObjects.stream().anyMatch({ it -> it.name == 'Andoria' })
	}

	void "gets Omicron Ceti III by GUID"() {
		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = stapiRestClient.astronomicalObjectApi
				.astronomicalObjectGet('ASMA0000011534', null)

		then:
		astronomicalObjectFullResponse.astronomicalObject.name == 'Omicron Ceti III'
	}

}
