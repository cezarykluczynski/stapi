package com.cezarykluczynski.stapi.server.food.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse
import com.cezarykluczynski.stapi.server.food.reader.FoodSoapReader
import spock.lang.Specification

class FoodSoapEndpointTest extends Specification {

	private FoodSoapReader foodSoapReaderMock

	private FoodSoapEndpoint foodSoapEndpoint

	void setup() {
		foodSoapReaderMock = Mock()
		foodSoapEndpoint = new FoodSoapEndpoint(foodSoapReaderMock)
	}

	void "passes base call to FoodSoapReader"() {
		given:
		FoodBaseRequest foodRequest = Mock()
		FoodBaseResponse foodResponse = Mock()

		when:
		FoodBaseResponse foodResponseResult = foodSoapEndpoint.getFoodBase(foodRequest)

		then:
		1 * foodSoapReaderMock.readBase(foodRequest) >> foodResponse
		foodResponseResult == foodResponse
	}

	void "passes full call to FoodSoapReader"() {
		given:
		FoodFullRequest foodFullRequest = Mock()
		FoodFullResponse foodFullResponse = Mock()

		when:
		FoodFullResponse foodResponseResult = foodSoapEndpoint.getFoodFull(foodFullRequest)

		then:
		1 * foodSoapReaderMock.readFull(foodFullRequest) >> foodFullResponse
		foodResponseResult == foodFullResponse
	}

}
