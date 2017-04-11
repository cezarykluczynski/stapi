package com.cezarykluczynski.stapi.server.food.reader

import com.cezarykluczynski.stapi.client.v1.soap.FoodBase
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodFull
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingGUIDException
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper
import com.cezarykluczynski.stapi.server.food.query.FoodSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class FoodSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private FoodSoapQuery foodSoapQueryBuilderMock

	private FoodBaseSoapMapper foodBaseSoapMapperMock

	private FoodFullSoapMapper foodFullSoapMapperMock

	private PageMapper pageMapperMock

	private FoodSoapReader foodSoapReader

	void setup() {
		foodSoapQueryBuilderMock = Mock()
		foodBaseSoapMapperMock = Mock()
		foodFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		foodSoapReader = new FoodSoapReader(foodSoapQueryBuilderMock, foodBaseSoapMapperMock, foodFullSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Food> foodList = Lists.newArrayList()
		Page<Food> foodPage = Mock()
		List<FoodBase> soapFoodList = Lists.newArrayList(new FoodBase(guid: GUID))
		FoodBaseRequest foodBaseRequest = Mock()
		ResponsePage responsePage = Mock()

		when:
		FoodBaseResponse foodResponse = foodSoapReader.readBase(foodBaseRequest)

		then:
		1 * foodSoapQueryBuilderMock.query(foodBaseRequest) >> foodPage
		1 * foodPage.content >> foodList
		1 * pageMapperMock.fromPageToSoapResponsePage(foodPage) >> responsePage
		1 * foodBaseSoapMapperMock.mapBase(foodList) >> soapFoodList
		foodResponse.foods[0].guid == GUID
		foodResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodFull foodFull = new FoodFull(guid: GUID)
		Food food = Mock()
		Page<Food> foodPage = Mock()
		FoodFullRequest foodFullRequest = new FoodFullRequest(guid: GUID)

		when:
		FoodFullResponse foodFullResponse = foodSoapReader.readFull(foodFullRequest)

		then:
		1 * foodSoapQueryBuilderMock.query(foodFullRequest) >> foodPage
		1 * foodPage.content >> Lists.newArrayList(food)
		1 * foodFullSoapMapperMock.mapFull(food) >> foodFull
		foodFullResponse.food.guid == GUID
	}

	void "requires GUID in full request"() {
		given:
		FoodFullRequest foodFullRequest = Mock()

		when:
		foodSoapReader.readFull(foodFullRequest)

		then:
		thrown(MissingGUIDException)
	}

}
