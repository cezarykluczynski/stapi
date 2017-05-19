package com.cezarykluczynski.stapi.server.food.mapper

import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.util.AbstractFoodTest

abstract class AbstractFoodMapperTest extends AbstractFoodTest {

	protected static Food createFood() {
		new Food(
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
	}

}
