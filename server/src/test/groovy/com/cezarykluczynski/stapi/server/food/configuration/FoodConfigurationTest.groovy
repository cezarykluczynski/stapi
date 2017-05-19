package com.cezarykluczynski.stapi.server.food.configuration

import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.food.endpoint.FoodSoapEndpoint
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class FoodConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private FoodConfiguration foodConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		foodConfiguration = new FoodConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Food SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = foodConfiguration.foodEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(FoodSoapEndpoint, FoodSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Food REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = foodConfiguration.foodServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(FoodRestEndpoint, FoodRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "FoodBaseSoapMapper is created"() {
		when:
		FoodBaseSoapMapper foodBaseSoapMapper = foodConfiguration.foodBaseSoapMapper()

		then:
		foodBaseSoapMapper != null
	}

	void "FoodFullSoapMapper is created"() {
		when:
		FoodFullSoapMapper foodFullSoapMapper = foodConfiguration.foodFullSoapMapper()

		then:
		foodFullSoapMapper != null
	}

	void "FoodBaseRestMapper is created"() {
		when:
		FoodBaseRestMapper foodBaseRestMapper = foodConfiguration.foodBaseRestMapper()

		then:
		foodBaseRestMapper != null
	}

	void "FoodFullRestMapper is created"() {
		when:
		FoodFullRestMapper foodFullRestMapper = foodConfiguration.foodFullRestMapper()

		then:
		foodFullRestMapper != null
	}

}
