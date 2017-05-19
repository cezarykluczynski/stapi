package com.cezarykluczynski.stapi.server.food.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_FOODS)
})
class FoodRestEndpointIntegrationTest extends AbstractFoodEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets food by UID"() {
		when:
		FoodFullResponse foodFullResponse = stapiRestClient.foodApi.foodGet('FOMA0000025922', null)

		then:
		foodFullResponse.food.name == 'Cake'
	}

	void "confirm that garlic soup is a soup of earthly origin"() {
		when:
		FoodBaseResponse foodBaseResponse = stapiRestClient.foodApi.foodSearchPost(null, null, null, null, null, true, null, null, null, null, true,
				null, null, null, null)

		then:
		foodBaseResponse.foods
				.stream()
				.anyMatch { it.name == 'Garlic soup' }
	}

}
