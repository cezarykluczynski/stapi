package com.cezarykluczynski.stapi.server.food.query

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.food.dto.FoodRequestDTO
import com.cezarykluczynski.stapi.model.food.repository.FoodRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FoodSoapQueryTest extends Specification {

	private FoodBaseSoapMapper foodBaseSoapMapperMock

	private FoodFullSoapMapper foodFullSoapMapperMock

	private PageMapper pageMapperMock

	private FoodRepository foodRepositoryMock

	private FoodSoapQuery foodSoapQuery

	void setup() {
		foodBaseSoapMapperMock = Mock()
		foodFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		foodRepositoryMock = Mock()
		foodSoapQuery = new FoodSoapQuery(foodBaseSoapMapperMock, foodFullSoapMapperMock, pageMapperMock, foodRepositoryMock)
	}

	void "maps FoodBaseRequest to FoodRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		FoodBaseRequest foodRequest = Mock()
		foodRequest.page >> requestPage
		FoodRequestDTO foodRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = foodSoapQuery.query(foodRequest)

		then:
		1 * foodBaseSoapMapperMock.mapBase(foodRequest) >> foodRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * foodRepositoryMock.findMatching(foodRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps FoodFullRequest to FoodRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		FoodFullRequest foodRequest = Mock()
		FoodRequestDTO foodRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = foodSoapQuery.query(foodRequest)

		then:
		1 * foodFullSoapMapperMock.mapFull(foodRequest) >> foodRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * foodRepositoryMock.findMatching(foodRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
