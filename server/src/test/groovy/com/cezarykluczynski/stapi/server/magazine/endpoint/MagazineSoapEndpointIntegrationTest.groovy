package com.cezarykluczynski.stapi.server.magazine.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINES)
})
class MagazineSoapEndpointIntegrationTest extends AbstractMagazineEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets 'Star Trek: Leonard Nimoy 1931-2015' by UID"() {
		when:
		MagazineFullResponse magazineFullResponse = stapiSoapClient.magazinePortType.getMagazineFull(new MagazineFullRequest(
				uid: 'MAMA0000203222'
		))

		then:
		magazineFullResponse.magazine.title == 'Star Trek: Leonard Nimoy 1931-2015'
	}

	void "gets 'Star Trek Magazine' issues published in 2003 and 2004"() {
		when:
		MagazineBaseResponse magazineBaseResponse = stapiSoapClient.magazinePortType.getMagazineBase(new MagazineBaseRequest(
				title: 'Star Trek Magazine',
				publishedYear: new IntegerRange(
						from: 2003,
						to: 2004)))

		then:
		magazineBaseResponse.magazine.stream()
				.anyMatch { it.title == 'Star Trek Magazine issue 111' }
		magazineBaseResponse.magazine.stream()
				.anyMatch { it.title == 'Star Trek Magazine issue 115' }
	}

}
