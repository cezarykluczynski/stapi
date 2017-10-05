package com.cezarykluczynski.stapi.server.astronomical_object.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectTypeEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ASTRONOMICAL_OBJECTS)
})
class AstronomicalObjectSoapEndpointIntegrationTest extends AbstractAstronomicalObjectEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets Selay by UID"() {
		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = stapiSoapClient.astronomicalObjectPortType
				.getAstronomicalObjectFull(new AstronomicalObjectFullRequest(uid: 'ASMA0000000810'))

		then:
		astronomicalObjectFullResponse.astronomicalObject.name == 'Selay'
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.LINK_ASTRONOMICAL_OBJECTS)
	})
	void "gets all planets in Bajoran system"() {
		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponse = stapiSoapClient.astronomicalObjectPortType
				.getAstronomicalObjectBase(new AstronomicalObjectBaseRequest(
				locationUid: 'ASMA0000000708',
				astronomicalObjectType: AstronomicalObjectTypeEnum.PLANET))

		then:
		astronomicalObjectResponse.astronomicalObjects.size() == 14
	}

}
