package com.cezarykluczynski.stapi.server.food.endpoint

import com.cezarykluczynski.stapi.client.api.dto.FoodSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_FOODS)
})
class FoodRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets food by UID"() {
		when:
		FoodFullResponse foodFullResponse = stapiRestClient.food.get('FOMA0000025922')

		then:
		foodFullResponse.food.name == 'Cake'
	}

	void "garlic soup is a soup of earthly origin"() {
		given:
		FoodSearchCriteria foodSearchCriteria = new FoodSearchCriteria(
				earthlyOrigin: true,
				soup: true
		)

		when:
		FoodBaseResponse foodBaseResponse = stapiRestClient.food.search(foodSearchCriteria)

		then:
		foodBaseResponse.foods
				.stream()
				.anyMatch { it.name == 'Garlic soup' }
	}

}
