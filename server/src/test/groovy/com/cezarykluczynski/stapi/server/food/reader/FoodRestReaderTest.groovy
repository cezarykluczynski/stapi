package com.cezarykluczynski.stapi.server.food.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBase
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFull
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper
import com.cezarykluczynski.stapi.server.food.query.FoodRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class FoodRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private FoodRestQuery foodRestQueryBuilderMock

	private FoodBaseRestMapper foodBaseRestMapperMock

	private FoodFullRestMapper foodFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private FoodRestReader foodRestReader

	void setup() {
		foodRestQueryBuilderMock = Mock()
		foodBaseRestMapperMock = Mock()
		foodFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		foodRestReader = new FoodRestReader(foodRestQueryBuilderMock, foodBaseRestMapperMock, foodFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodBase foodBase = Mock()
		Food food = Mock()
		FoodRestBeanParams foodRestBeanParams = Mock()
		List<FoodBase> restFoodList = Lists.newArrayList(foodBase)
		List<Food> foodList = Lists.newArrayList(food)
		Page<Food> foodPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		FoodBaseResponse foodResponseOutput = foodRestReader.readBase(foodRestBeanParams)

		then:
		1 * foodRestQueryBuilderMock.query(foodRestBeanParams) >> foodPage
		1 * pageMapperMock.fromPageToRestResponsePage(foodPage) >> responsePage
		1 * foodRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * foodPage.content >> foodList
		1 * foodBaseRestMapperMock.mapBase(foodList) >> restFoodList
		0 * _
		foodResponseOutput.foods == restFoodList
		foodResponseOutput.page == responsePage
		foodResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodFull foodFull = Mock()
		Food food = Mock()
		List<Food> foodList = Lists.newArrayList(food)
		Page<Food> foodPage = Mock()

		when:
		FoodFullResponse foodResponseOutput = foodRestReader.readFull(UID)

		then:
		1 * foodRestQueryBuilderMock.query(_ as FoodRestBeanParams) >> { FoodRestBeanParams foodRestBeanParams ->
			assert foodRestBeanParams.uid == UID
			foodPage
		}
		1 * foodPage.content >> foodList
		1 * foodFullRestMapperMock.mapFull(food) >> foodFull
		0 * _
		foodResponseOutput.food == foodFull
	}

	void "requires UID in full request"() {
		when:
		foodRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
