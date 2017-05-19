package com.cezarykluczynski.stapi.server.food.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_FOODS)
})
class FoodSoapEndpointIntegrationTest extends AbstractFoodEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets food by UID"() {
		when:
		FoodFullResponse foodFullResponse = stapiSoapClient.foodPortType.getFoodFull(new FoodFullRequest(uid: 'FOMA0000033877'))

		then:
		foodFullResponse.food.name == 'Tennessee whiskey'
	}

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "Vulcan mint is among Vulcan spices"() {
		when:
		FoodBaseResponse foodFullResponse = stapiSoapClient.foodPortType.getFoodBase(new FoodBaseRequest(
				name: 'Vulcan',
				herbOrSpice: true
		))

		then:
		foodFullResponse.foods
				.stream()
				.anyMatch({ it.name == 'Vulcan mint' })
	}

}
