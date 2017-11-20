package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType
import spock.lang.Specification

class FoodTest extends Specification {

	private FoodPortType foodPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Food food

	void setup() {
		foodPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		food = new Food(foodPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		FoodBaseRequest foodBaseRequest = Mock()
		FoodBaseResponse foodBaseResponse = Mock()

		when:
		FoodBaseResponse foodBaseResponseOutput = food.search(foodBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(foodBaseRequest)
		1 * foodPortTypeMock.getFoodBase(foodBaseRequest) >> foodBaseResponse
		0 * _
		foodBaseResponse == foodBaseResponseOutput
	}

	void "searches entities"() {
		given:
		FoodFullRequest foodFullRequest = Mock()
		FoodFullResponse foodFullResponse = Mock()

		when:
		FoodFullResponse foodFullResponseOutput = food.get(foodFullRequest)

		then:
		1 * apiKeySupplierMock.supply(foodFullRequest)
		1 * foodPortTypeMock.getFoodFull(foodFullRequest) >> foodFullResponse
		0 * _
		foodFullResponse == foodFullResponseOutput
	}

}
