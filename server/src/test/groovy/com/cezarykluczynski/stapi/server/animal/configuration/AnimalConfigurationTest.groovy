package com.cezarykluczynski.stapi.server.animal.configuration

import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalRestEndpoint
import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalSoapEndpoint
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class AnimalConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private AnimalConfiguration animalConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		animalConfiguration = new AnimalConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Animal SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = animalConfiguration.animalEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(AnimalSoapEndpoint, AnimalSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Animal REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = animalConfiguration.animalServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(AnimalRestEndpoint, AnimalRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "AnimalBaseSoapMapper is created"() {
		when:
		AnimalBaseSoapMapper animalBaseSoapMapper = animalConfiguration.animalBaseSoapMapper()

		then:
		animalBaseSoapMapper != null
	}

	void "AnimalFullSoapMapper is created"() {
		when:
		AnimalFullSoapMapper animalFullSoapMapper = animalConfiguration.animalFullSoapMapper()

		then:
		animalFullSoapMapper != null
	}

	void "AnimalBaseRestMapper is created"() {
		when:
		AnimalBaseRestMapper animalBaseRestMapper = animalConfiguration.animalBaseRestMapper()

		then:
		animalBaseRestMapper != null
	}

	void "AnimalFullRestMapper is created"() {
		when:
		AnimalFullRestMapper animalFullRestMapper = animalConfiguration.animalFullRestMapper()

		then:
		animalFullRestMapper != null
	}

}
