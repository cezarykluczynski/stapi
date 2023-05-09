package com.cezarykluczynski.stapi.etl.food.creation.processor

import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class FoodWriterTest extends Specification {

	private FoodRepository foodRepositoryMock

	private FoodWriter foodWriterMock

	void setup() {
		foodRepositoryMock = Mock()
		foodWriterMock = new FoodWriter(foodRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Food food = new Food()
		List<Food> foodList = Lists.newArrayList(food)

		when:
		foodWriterMock.write(new Chunk(foodList))

		then:
		1 * foodRepositoryMock.saveAll(foodList)
		0 * _
	}

}
