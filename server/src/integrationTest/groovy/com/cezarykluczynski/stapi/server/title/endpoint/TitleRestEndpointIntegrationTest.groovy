package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.api.dto.TitleV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.TitleV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TitleV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TITLES)
})
class TitleRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets title by UID"() {
		when:
		TitleV2FullResponse titleV2FullResponse = stapiRestClient.title.getV2('TIMA0000006319')

		then:
		titleV2FullResponse.title.name == 'DaiMon'
	}

	void "High Cleric is among religious titles"() {
		given:
		TitleV2SearchCriteria titleV2SearchCriteria = new TitleV2SearchCriteria(
				religiousTitle: true
		)

		when:
		TitleV2BaseResponse titleV2BaseResponse = stapiRestClient.title.searchV2(titleV2SearchCriteria)

		then:
		titleV2BaseResponse.titles
				.stream()
				.anyMatch { it -> it.name == 'High Cleric' }
	}

}
