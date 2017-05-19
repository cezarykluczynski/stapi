package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.client.v1.soap.FoodHeader
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class FoodHeaderSoapMapperTest extends AbstractFoodMapperTest {

	private FoodHeaderSoapMapper foodHeaderSoapMapper

	void setup() {
		foodHeaderSoapMapper = Mappers.getMapper(FoodHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Food food = new Food(
				uid: UID,
				name: NAME)

		when:
		FoodHeader foodHeader = foodHeaderSoapMapper.map(Lists.newArrayList(food))[0]

		then:
		foodHeader.uid == UID
		foodHeader.name == NAME
	}

}
