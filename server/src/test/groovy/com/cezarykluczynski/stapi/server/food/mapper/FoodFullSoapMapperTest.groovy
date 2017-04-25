package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.client.v1.soap.FoodFull
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO
import com.cezarykluczynski.stapi.model.food.entity.Food
import org.mapstruct.factory.Mappers

class FoodFullSoapMapperTest extends AbstractFoodMapperTest {

	private FoodFullSoapMapper foodFullSoapMapper

	void setup() {
		foodFullSoapMapper = Mappers.getMapper(FoodFullSoapMapper)
	}

	void "maps SOAP FoodFullRequest to FoodBaseRequestDTO"() {
		given:
		FoodFullRequest foodFullRequest = new FoodFullRequest(uid: UID)

		when:
		FoodRequestDTO foodRequestDTO = foodFullSoapMapper.mapFull foodFullRequest

		then:
		foodRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Food food = createFood()

		when:
		FoodFull foodFull = foodFullSoapMapper.mapFull(food)

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
