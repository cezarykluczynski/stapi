package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.api.dto.LocationV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LOCATIONS)
})
class LocationRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets location by UID"() {
		when:
		LocationV2FullResponse locationV2FullResponse = stapiRestClient.location.getV2('LOMA0000005162')

		then:
		locationV2FullResponse.location.name == 'Canada'
	}

	void "Tilonus Institute for Mental Disorders is among fictional medical establishments"() {
		given:
		LocationV2SearchCriteria locationV2SearchCriteria = new LocationV2SearchCriteria(
				fictionalLocation: true,
				medicalEstablishment: true
		)

		when:
		LocationV2BaseResponse locationV2BaseResponse = stapiRestClient.location.searchV2(locationV2SearchCriteria)

		then:
		locationV2BaseResponse.locations
				.stream()
				.anyMatch { it -> it.name == 'Tilonus Institute for Mental Disorders' }
	}

}
