package com.cezarykluczynski.stapi.server.astronomical_object.endpoint

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.areStepsCompleted(StepName.CREATE_ASTRONOMICAL_OBJECTS, StepName.LINK_ASTRONOMICAL_OBJECTS)
})
class AstronomicalObjectRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets Omicron Ceti III by UID"() {
		when:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponse = stapiRestClient.astronomicalObject.getV2('ASMA0000011534')

		then:
		astronomicalObjectV2FullResponse.astronomicalObject.name == 'Omicron Ceti III'
		astronomicalObjectV2FullResponse.astronomicalObject.location != null
	}

	void "gets Alpha Quadrant by UID"() {
		when:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponse = stapiRestClient.astronomicalObject.getV2('ASMA0000025892')

		then:
		astronomicalObjectV2FullResponse.astronomicalObject.name == 'Alpha Quadrant'
		astronomicalObjectV2FullResponse.astronomicalObject.astronomicalObjects.size() > 740
	}

	void "finds Tarok by astronomical object type"() {
		given:
		AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria = new AstronomicalObjectV2SearchCriteria(
				astronomicalObjectType: AstronomicalObjectV2Type.M_CLASS_MOON
		)

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectResponse = stapiRestClient.astronomicalObject.searchV2(astronomicalObjectV2SearchCriteria)

		then:
		astronomicalObjectResponse.astronomicalObjects.stream()
				.anyMatch { it -> it.name == 'Tarok' }
	}

	void "finds Borg spatial designations"() {
		given:
		AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria = new AstronomicalObjectV2SearchCriteria(
				astronomicalObjectType: AstronomicalObjectV2Type.BORG_SPATIAL_DESIGNATION
		)

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectResponse = stapiRestClient.astronomicalObject.searchV2(astronomicalObjectV2SearchCriteria)

		then:
		astronomicalObjectResponse.astronomicalObjects.any { it.name.startsWith('Galactic Cluster ') }
		astronomicalObjectResponse.astronomicalObjects.any { it.name.startsWith('Grid ') }
		astronomicalObjectResponse.astronomicalObjects.any { it.name.startsWith('Unimatrix ') }
	}

}
