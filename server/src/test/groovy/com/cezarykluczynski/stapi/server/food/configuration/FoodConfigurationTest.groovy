package com.cezarykluczynski.stapi.server.food.configuration

import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint
import com.cezarykluczynski.stapi.server.food.endpoint.FoodSoapEndpoint
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodBaseSoapMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullRestMapper
import com.cezarykluczynski.stapi.server.food.mapper.FoodFullSoapMapper
import com.cezarykluczynski.stapi.server.food.reader.FoodRestReader
import com.cezarykluczynski.stapi.server.food.reader.FoodSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class FoodConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private FoodConfiguration foodConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		foodConfiguration = new FoodConfiguration(applicationContext: applicationContextMock)
	}

	void "Food SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		FoodSoapReader foodSoapReaderMock = Mock(FoodSoapReader)

		when:
		Endpoint foodSoapEndpoint = foodConfiguration.foodSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(FoodSoapReader) >> foodSoapReaderMock
		0 * _
		foodSoapEndpoint != null
		((EndpointImpl) foodSoapEndpoint).implementor instanceof FoodSoapEndpoint
		((EndpointImpl) foodSoapEndpoint).bus == springBus
		foodSoapEndpoint.published
	}

	void "FoodRestEndpoint is created"() {
		given:
		FoodRestReader foodRestMapper = Mock(FoodRestReader)

		when:
		FoodRestEndpoint foodRestEndpoint = foodConfiguration.foodRestEndpoint()

		then:
		1 * applicationContextMock.getBean(FoodRestReader) >> foodRestMapper
		0 * _
		foodRestEndpoint != null
		foodRestEndpoint.foodRestReader == foodRestMapper
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
