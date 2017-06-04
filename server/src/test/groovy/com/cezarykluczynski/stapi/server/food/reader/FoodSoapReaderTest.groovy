package com.cezarykluczynski.stapi.server.food.reader

import com.cezarykluczynski.stapi.client.v1.soap.FoodBase
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodFull
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.food.entity.Food
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper
import com.cezarykluczynski.stapi.server.food.query.FoodSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class FoodSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private FoodSoapQuery foodSoapQueryBuilderMock

	private FoodBaseSoapMapper foodBaseSoapMapperMock

	private FoodFullSoapMapper foodFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private FoodSoapReader foodSoapReader

	void setup() {
		foodSoapQueryBuilderMock = Mock()
		foodBaseSoapMapperMock = Mock()
		foodFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		foodSoapReader = new FoodSoapReader(foodSoapQueryBuilderMock, foodBaseSoapMapperMock, foodFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Food> foodList = Lists.newArrayList()
		Page<Food> foodPage = Mock()
		List<FoodBase> soapFoodList = Lists.newArrayList(new FoodBase(uid: UID))
		FoodBaseRequest foodBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		FoodBaseResponse foodResponse = foodSoapReader.readBase(foodBaseRequest)

		then:
		1 * foodSoapQueryBuilderMock.query(foodBaseRequest) >> foodPage
		1 * foodPage.content >> foodList
		1 * pageMapperMock.fromPageToSoapResponsePage(foodPage) >> responsePage
		1 * foodBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * foodBaseSoapMapperMock.mapBase(foodList) >> soapFoodList
		0 * _
		foodResponse.foods[0].uid == UID
		foodResponse.page == responsePage
		foodResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		FoodFull foodFull = new FoodFull(uid: UID)
		Food food = Mock()
		Page<Food> foodPage = Mock()
		FoodFullRequest foodFullRequest = new FoodFullRequest(uid: UID)

		when:
		FoodFullResponse foodFullResponse = foodSoapReader.readFull(foodFullRequest)

		then:
		1 * foodSoapQueryBuilderMock.query(foodFullRequest) >> foodPage
		1 * foodPage.content >> Lists.newArrayList(food)
		1 * foodFullSoapMapperMock.mapFull(food) >> foodFull
		0 * _
		foodFullResponse.food.uid == UID
	}

	void "requires UID in full request"() {
		given:
		FoodFullRequest foodFullRequest = Mock()

		when:
		foodSoapReader.readFull(foodFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
