package com.cezarykluczynski.stapi.server.magazine.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINES)
})
class MagazineRestEndpointIntegrationTest extends AbstractMagazineEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets magazine by UID"() {
		when:
		MagazineFullResponse magazineFullResponse = stapiRestClient.magazineApi.magazineGet('MAMA0000203315', null)

		then:
		magazineFullResponse.magazine.title == 'Star Trek: Heroes and Villains'
	}

	void "'Star Trek Magazine Souvenir Special' is among magazines with more than 120 pages"() {
		when:
		MagazineBaseResponse magazineBaseResponse = stapiRestClient.magazineApi
				.magazineSearchPost(0, 20, null, null, null, null, null, 120, null)

		then:
		magazineBaseResponse.magazines.stream()
				.anyMatch { it.title == 'Star Trek Magazine Souvenir Special' }
	}

}
