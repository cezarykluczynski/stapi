package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.FoodApi
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse
import com.cezarykluczynski.stapi.util.AbstractFoodTest

class FoodTest extends AbstractFoodTest {

	private FoodApi foodApiMock

	private Food food

	void setup() {
		foodApiMock = Mock()
		food = new Food(foodApiMock)
	}

	void "gets single entity"() {
		given:
		FoodFullResponse foodFullResponse = Mock()

		when:
		FoodFullResponse foodFullResponseOutput = food.get(UID)

		then:
		1 * foodApiMock.v1RestFoodGet(UID, null) >> foodFullResponse
		0 * _
		foodFullResponse == foodFullResponseOutput
	}

	void "searches entities"() {
		given:
		FoodBaseResponse foodBaseResponse = Mock()

		when:
		FoodBaseResponse foodBaseResponseOutput = food.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, EARTHLY_ORIGIN, DESSERT, FRUIT, HERB_OR_SPICE,
				SAUCE, SOUP, BEVERAGE, ALCOHOLIC_BEVERAGE, JUICE, TEA)

		then:
		1 * foodApiMock.v1RestFoodSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, EARTHLY_ORIGIN, DESSERT, FRUIT, HERB_OR_SPICE, SAUCE, SOUP,
				BEVERAGE, ALCOHOLIC_BEVERAGE, JUICE, TEA) >> foodBaseResponse
		0 * _
		foodBaseResponse == foodBaseResponseOutput
	}

}
