package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBase
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class FoodBaseRestMapperTest extends AbstractFoodMapperTest {

	private FoodBaseRestMapper foodBaseRestMapper

	void setup() {
		foodBaseRestMapper = Mappers.getMapper(FoodBaseRestMapper)
	}

	void "maps FoodRestBeanParams to FoodRequestDTO"() {
		given:
		FoodRestBeanParams foodRestBeanParams = new FoodRestBeanParams(
				uid: UID,
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

		when:
		FoodRequestDTO foodRequestDTO = foodBaseRestMapper.mapBase foodRestBeanParams

		then:
		foodRequestDTO.uid == UID
		foodRequestDTO.name == NAME
		foodRequestDTO.earthlyOrigin == EARTHLY_ORIGIN
		foodRequestDTO.dessert == DESSERT
		foodRequestDTO.fruit == FRUIT
		foodRequestDTO.herbOrSpice == HERB_OR_SPICE
		foodRequestDTO.sauce == SAUCE
		foodRequestDTO.soup == SOUP
		foodRequestDTO.beverage == BEVERAGE
		foodRequestDTO.alcoholicBeverage == ALCOHOLIC_BEVERAGE
		foodRequestDTO.juice == JUICE
		foodRequestDTO.tea == TEA
	}

	void "maps DB entity to base REST entity"() {
		given:
		Food food = createFood()

		when:
		FoodBase foodBase = foodBaseRestMapper.mapBase(Lists.newArrayList(food))[0]

		then:
		foodBase.uid == UID
		foodBase.name == NAME
		foodBase.earthlyOrigin == EARTHLY_ORIGIN
		foodBase.dessert == DESSERT
		foodBase.fruit == FRUIT
		foodBase.herbOrSpice == HERB_OR_SPICE
		foodBase.sauce == SAUCE
		foodBase.soup == SOUP
		foodBase.beverage == BEVERAGE
		foodBase.alcoholicBeverage == ALCOHOLIC_BEVERAGE
		foodBase.juice == JUICE
		foodBase.tea == TEA
	}

}
