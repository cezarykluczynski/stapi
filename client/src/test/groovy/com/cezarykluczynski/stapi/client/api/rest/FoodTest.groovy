package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.FoodSearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.FoodApi
import com.cezarykluczynski.stapi.client.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.FoodFullResponse
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
		1 * foodApiMock.v1Get(UID) >> foodFullResponse
		0 * _
		foodFullResponse == foodFullResponseOutput
	}

	void "searches entities"() {
		given:
		FoodBaseResponse foodBaseResponse = Mock()

		when:
		FoodBaseResponse foodBaseResponseOutput = food.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EARTHLY_ORIGIN, DESSERT, FRUIT,
				HERB_OR_SPICE, SAUCE, SOUP, BEVERAGE, ALCOHOLIC_BEVERAGE, JUICE, TEA)

		then:
		1 * foodApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EARTHLY_ORIGIN, DESSERT, FRUIT, HERB_OR_SPICE,
				SAUCE, SOUP, BEVERAGE, ALCOHOLIC_BEVERAGE, JUICE, TEA) >> foodBaseResponse
		0 * _
		foodBaseResponse == foodBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		FoodBaseResponse foodBaseResponse = Mock()
		FoodSearchCriteria foodSearchCriteria = new FoodSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				earthlyOrigin: EARTHLY_ORIGIN,
				dessert: DESSERT,
				fruit: FRUIT,
				herbOrSpice: HERB_OR_SPICE,
				sauce: SAUCE,
				soup: SOUP,
				beverage: BEVERAGE,
				alcoholicBeverage: ALCOHOLIC_BEVERAGE,
				juice: JUICE,
				tea: TEA)
		foodSearchCriteria.sort.addAll(SORT)

		when:
		FoodBaseResponse foodBaseResponseOutput = food.search(foodSearchCriteria)

		then:
		1 * foodApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, EARTHLY_ORIGIN, DESSERT, FRUIT, HERB_OR_SPICE,
				SAUCE, SOUP, BEVERAGE, ALCOHOLIC_BEVERAGE, JUICE, TEA) >> foodBaseResponse
		0 * _
		foodBaseResponse == foodBaseResponseOutput
	}

}
