package com.cezarykluczynski.stapi.server.element.endpoint

import com.cezarykluczynski.stapi.client.rest.model.ElementV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ElementV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ElementV2SearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_ELEMENTS)
})
class ElementRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets element by UID"() {
		when:
		ElementV2FullResponse elementV2FullResponse = stapiRestClient.element.getV2('ELMA0000028971')

		then:
		elementV2FullResponse.element.name == 'Mazdaium'
	}

	void "Zeppo is in both World series and Mega series"() {
		ElementV2SearchCriteria elementV2SearchCriteria = new ElementV2SearchCriteria(
				worldSeries: true,
				megaSeries: true
		)

		when:
		ElementV2BaseResponse elementV2BaseResponse = stapiRestClient.element.searchV2(elementV2SearchCriteria)

		then:
		elementV2BaseResponse.elements
				.stream()
				.anyMatch { it.name == 'Zeppo' }
	}

}
