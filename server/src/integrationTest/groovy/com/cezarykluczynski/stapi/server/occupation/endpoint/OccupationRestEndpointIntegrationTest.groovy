package com.cezarykluczynski.stapi.server.occupation.endpoint

import com.cezarykluczynski.stapi.client.rest.model.OccupationV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2SearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_OCCUPATIONS)
})
class OccupationRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets occupation by UID"() {
		when:
		OccupationV2FullResponse occupationV2FullResponse = stapiRestClient.occupation.getV2('OCMA0000221770')

		then:
		occupationV2FullResponse.occupation.name == 'Designer'
	}

	void "Coroner is among legal and medical occupations"() {
		given:
		OccupationV2SearchCriteria occupationV2SearchCriteria = new OccupationV2SearchCriteria(
				legalOccupation: true,
				medicalOccupation: true
		)

		when:
		OccupationV2BaseResponse occupationV2BaseResponse = stapiRestClient.occupation.searchV2(occupationV2SearchCriteria)

		then:
		occupationV2BaseResponse.occupations
				.stream()
				.anyMatch { it.name == 'Coroner' }
	}

}
