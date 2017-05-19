package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LOCATIONS)
})
class LocationRestEndpointIntegrationTest extends AbstractLocationEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets location by UID"() {
		when:
		LocationFullResponse locationFullResponse = stapiRestClient.locationApi.locationGet('LOMA0000005162', null)

		then:
		locationFullResponse.location.name == 'Canada'
	}

	void "Tilonus Institute for Mental Disorders is among fictional medical establishments"() {
		when:
		LocationBaseResponse locationBaseResponse = stapiRestClient.locationApi.locationSearchPost(null, null, null, null, null, null, true, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, true, null, null, null, null)

		then:
		locationBaseResponse.locations
				.stream()
				.anyMatch { it -> it.name == 'Tilonus Institute for Mental Disorders' }
	}

}
