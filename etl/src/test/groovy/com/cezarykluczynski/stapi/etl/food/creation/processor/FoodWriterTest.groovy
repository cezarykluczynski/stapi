package com.cezarykluczynski.stapi.etl.food.creation.processor

import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class FoodWriterTest extends Specification {

	private FoodRepository foodRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private FoodWriter foodWriterMock

	void setup() {
		foodRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		foodWriterMock = new FoodWriter(foodRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Food food = new Food()
		List<Food> foodList = Lists.newArrayList(food)

		when:
		foodWriterMock.write(foodList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Food) >> { args ->
			assert args[0][0] == food
			foodList
		}
		1 * foodRepositoryMock.save(foodList)
		0 * _
	}

}
