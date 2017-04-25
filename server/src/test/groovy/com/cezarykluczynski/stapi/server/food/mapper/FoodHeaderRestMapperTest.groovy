package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodHeader
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class FoodHeaderRestMapperTest extends AbstractFoodMapperTest {

	private FoodHeaderRestMapper foodHeaderRestMapper

	void setup() {
		foodHeaderRestMapper = Mappers.getMapper(FoodHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Food food = new Food(
				uid: UID,
				name: NAME)

		when:
		FoodHeader foodHeader = foodHeaderRestMapper.map(Lists.newArrayList(food))[0]

		then:
		foodHeader.uid == UID
		foodHeader.name == NAME
	}

}
