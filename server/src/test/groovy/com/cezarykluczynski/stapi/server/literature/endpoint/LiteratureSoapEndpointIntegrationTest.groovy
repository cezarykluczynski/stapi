package com.cezarykluczynski.stapi.server.literature.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_LITERATURE)
})
class LiteratureSoapEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets literature by UID"() {
		when:
		LiteratureFullResponse literatureFullResponse = stapiSoapClient.literaturePortType.getLiteratureFull(new LiteratureFullRequest(
				uid: 'LIMA0000092352'
		))

		then:
		literatureFullResponse.literature.title == 'Where No Man Has Gone Before'
	}

	void "'History of Medical Psychology' is among scientific literature with earthly origin"() {
		when:
		LiteratureBaseResponse literatureBaseResponse = stapiSoapClient.literaturePortType.getLiteratureBase(new LiteratureBaseRequest(
				earthlyOrigin: true,
				scientificLiterature: true
		))

		then:
		literatureBaseResponse.literature
				.stream()
				.anyMatch { it -> it.title == 'History of Medical Psychology' }
	}

}
