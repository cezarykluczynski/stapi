package com.cezarykluczynski.stapi.server.title.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TitleBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TITLES)
})
class TitleRestEndpointIntegrationTest extends AbstractTitleEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets title by UID"() {
		when:
		TitleFullResponse titleFullResponse = stapiRestClient.titleApi.titleGet('TIMA0000006319', null)

		then:
		titleFullResponse.title.name == 'DaiMon'
	}

	void "Cleric is among religious titles"() {
		when:
		TitleBaseResponse titleBaseResponse = stapiRestClient.titleApi.titleSearchPost(null, null, null, null, null, null, null, true, null, null)

		then:
		titleBaseResponse.titles
				.stream()
				.anyMatch { it -> it.name == 'Cleric' }
	}

}
