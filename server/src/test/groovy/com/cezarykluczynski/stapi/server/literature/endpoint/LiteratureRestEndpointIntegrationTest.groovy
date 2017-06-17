package com.cezarykluczynski.stapi.server.literature.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LITERATURE)
})
class LiteratureRestEndpointIntegrationTest extends AbstractLiteratureEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets literature by UID"() {
		when:
		LiteratureFullResponse literatureFullResponse = stapiRestClient.literatureApi.literatureGet('LIMA0000084827', null)

		then:
		literatureFullResponse.literature.title == 'Selected Works of Jirex'
	}

	void "Bible is among religious earthly literature"() {
		when:
		LiteratureBaseResponse literatureBaseResponse = stapiRestClient.literatureApi.literatureSearchPost(null, null, null, null, null, true,
		null, null, null, null, true)

		then:
		literatureBaseResponse.literature
				.stream()
				.anyMatch { it -> it.title == 'Bible' }
	}

}
