package com.cezarykluczynski.stapi.server.food.query

import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.food.dto.FoodRestBeanParams
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FoodRestQueryTest extends Specification {

	private FoodBaseRestMapper foodBaseRestMapperMock

	private PageMapper pageMapperMock

	private FoodRepository foodRepositoryMock

	private FoodRestQuery foodRestQuery

	void setup() {
		foodBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		foodRepositoryMock = Mock()
		foodRestQuery = new FoodRestQuery(foodBaseRestMapperMock, pageMapperMock, foodRepositoryMock)
	}

	void "maps FoodRestBeanParams to FoodRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		FoodRestBeanParams foodRestBeanParams = Mock()
		FoodRequestDTO foodRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = foodRestQuery.query(foodRestBeanParams)

		then:
		1 * foodBaseRestMapperMock.mapBase(foodRestBeanParams) >> foodRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(foodRestBeanParams) >> pageRequest
		1 * foodRepositoryMock.findMatching(foodRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
