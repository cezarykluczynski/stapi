package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ASTRONOMICAL_OBJECTS) &&
			StaticJobCompletenessDecider.isStepCompleted(StepName.LINK_ASTRONOMICAL_OBJECTS)
})
class AstronomicalObjectSoapEndpointIntegrationTest extends AbstractAstronomicalObjectEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets all planets in Bajoran system"() {
		when:
		AstronomicalObjectResponse astronomicalObjectResponse = stapiSoapClient.astronomicalObjectPortType
				.getAstronomicalObjects(new AstronomicalObjectRequest(
				locationGuid: 'ASMA0000000708',
				astronomicalObjectType: AstronomicalObjectTypeEnum.PLANET))

		then:
		astronomicalObjectResponse.astronomicalObjects.size() == 14
	}

}
