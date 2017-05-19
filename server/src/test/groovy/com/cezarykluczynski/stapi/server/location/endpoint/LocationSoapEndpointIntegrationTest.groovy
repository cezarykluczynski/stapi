package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LOCATIONS)
})
class LocationSoapEndpointIntegrationTest extends AbstractLocationEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets location by UID"() {
		when:
		LocationFullResponse locationFullResponse = stapiSoapClient.locationPortType.getLocationFull(new LocationFullRequest(
				uid: 'LOMA0000054131'
		))

		then:
		locationFullResponse.location.name == 'Alabama'
	}

	void "Sacred Marketplace is among religious landmarks"() {
		when:
		LocationBaseResponse locationBaseResponse = stapiSoapClient.locationPortType.getLocationBase(new LocationBaseRequest(
				landmark: true,
				religiousLocation: true
		))

		then:
		locationBaseResponse.locations
				.stream()
				.anyMatch { it -> it.name == 'Sacred Marketplace' }
	}

}
