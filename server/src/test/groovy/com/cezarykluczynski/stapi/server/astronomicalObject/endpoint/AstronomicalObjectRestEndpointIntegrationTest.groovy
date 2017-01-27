package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectResponse
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
	void "gets Andoria by astronomical object type"() {
		when:
		AstronomicalObjectResponse astronomicalObjectResponse = stapiRestClient.astronomicalObjectApi.astronomicalObjectPost(0, 20, null, null, null,
				'M_CLASS_MOON', null)

		then:
		astronomicalObjectResponse.astronomicalObjects.stream().anyMatch({ it -> it.name == 'Andoria' })
	}

}
