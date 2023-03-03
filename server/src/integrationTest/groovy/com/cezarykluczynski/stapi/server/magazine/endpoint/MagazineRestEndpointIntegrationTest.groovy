package com.cezarykluczynski.stapi.server.magazine.endpoint

import com.cezarykluczynski.stapi.client.api.dto.MagazineSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_MAGAZINES)
})
class MagazineRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets magazine by UID"() {
		when:
		MagazineFullResponse magazineFullResponse = stapiRestClient.magazine.get('MAMA0000203315')

		then:
		magazineFullResponse.magazine.title == 'Star Trek: Heroes and Villains'
	}

	void "'Star Trek Magazine Souvenir Special' is among magazines with more than 120 pages"() {
		given:
		MagazineSearchCriteria magazineSearchCriteria = new MagazineSearchCriteria(
				numberOfPagesFrom: 120
		)

		when:
		MagazineBaseResponse magazineBaseResponse = stapiRestClient.magazine.search(magazineSearchCriteria)

		then:
		magazineBaseResponse.magazines.stream()
				.anyMatch { it.title == 'Star Trek Magazine Souvenir Special' }
	}

}
