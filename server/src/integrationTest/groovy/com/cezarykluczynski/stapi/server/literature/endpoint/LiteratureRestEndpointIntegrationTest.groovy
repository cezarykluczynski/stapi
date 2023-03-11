package com.cezarykluczynski.stapi.server.literature.endpoint

import com.cezarykluczynski.stapi.client.api.dto.LiteratureSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LITERATURE)
})
class LiteratureRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets literature by UID"() {
		when:
		LiteratureFullResponse literatureFullResponse = stapiRestClient.literature.get('LIMA0000084827')

		then:
		literatureFullResponse.literature.title == 'Selected Works of Jirex'
	}

	void "Bible is among religious earthly literature"() {
		given:
		LiteratureSearchCriteria literatureSearchCriteria = new LiteratureSearchCriteria(
				earthlyOrigin: true,
				religiousLiterature: true
		)

		when:
		LiteratureBaseResponse literatureBaseResponse = stapiRestClient.literature.search(literatureSearchCriteria)

		then:
		literatureBaseResponse.literature
				.stream()
				.anyMatch { it -> it.title == 'Bible' }
	}

}
