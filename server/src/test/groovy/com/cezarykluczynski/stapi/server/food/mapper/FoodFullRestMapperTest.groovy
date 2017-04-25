package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFull
import com.cezarykluczynski.stapi.model.food.entity.Food
import org.mapstruct.factory.Mappers

class FoodFullRestMapperTest extends AbstractFoodMapperTest {

	private FoodFullRestMapper foodFullRestMapper

	void setup() {
		foodFullRestMapper = Mappers.getMapper(FoodFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Food dBFood = createFood()

		when:
		FoodFull foodFull = foodFullRestMapper.mapFull(dBFood)

		then:
		foodFull.uid == UID
		foodFull.name == NAME
		foodFull.earthlyOrigin == EARTHLY_ORIGIN
		foodFull.dessert == DESSERT
		foodFull.fruit == FRUIT
		foodFull.herbOrSpice == HERB_OR_SPICE
		foodFull.sauce == SAUCE
		foodFull.soup == SOUP
		foodFull.beverage == BEVERAGE
		foodFull.alcoholicBeverage == ALCOHOLIC_BEVERAGE
		foodFull.juice == JUICE
		foodFull.tea == TEA
	}

}
