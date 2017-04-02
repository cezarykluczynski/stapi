package com.cezarykluczynski.stapi.server.food.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBase
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFull
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper
import com.cezarykluczynski.stapi.server.food.query.FoodRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class FoodRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private FoodRestQuery foodRestQueryBuilderMock

	private FoodBaseRestMapper foodBaseRestMapperMock

	private FoodFullRestMapper foodFullRestMapperMock

	private PageMapper pageMapperMock

	private FoodRestReader foodRestReader

	void setup() {
		foodRestQueryBuilderMock = Mock(FoodRestQuery)
		foodBaseRestMapperMock = Mock(FoodBaseRestMapper)
		foodFullRestMapperMock = Mock(FoodFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		foodRestReader = new FoodRestReader(foodRestQueryBuilderMock, foodBaseRestMapperMock,
				foodFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodRestBeanParams foodRestBeanParams = Mock(FoodRestBeanParams)
		List<FoodBase> restFoodList = Lists.newArrayList(Mock(FoodBase))
		List<Food> foodList = Lists.newArrayList(Mock(Food))
		Page<Food> foodPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		FoodBaseResponse foodResponseOutput = foodRestReader.readBase(foodRestBeanParams)

		then:
		1 * foodRestQueryBuilderMock.query(foodRestBeanParams) >> foodPage
		1 * pageMapperMock.fromPageToRestResponsePage(foodPage) >> responsePage
		1 * foodPage.content >> foodList
		1 * foodBaseRestMapperMock.mapBase(foodList) >> restFoodList
		0 * _
		foodResponseOutput.foods == restFoodList
		foodResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodFull foodFull = Mock(FoodFull)
		Food food = Mock(Food)
		List<Food> foodList = Lists.newArrayList(food)
		Page<Food> foodPage = Mock(Page)

		when:
		FoodFullResponse foodResponseOutput = foodRestReader.readFull(GUID)

		then:
		1 * foodRestQueryBuilderMock.query(_ as FoodRestBeanParams) >> { FoodRestBeanParams foodRestBeanParams ->
			assert foodRestBeanParams.guid == GUID
			foodPage
		}
		1 * foodPage.content >> foodList
		1 * foodFullRestMapperMock.mapFull(food) >> foodFull
		0 * _
		foodResponseOutput.food == foodFull
	}

}
