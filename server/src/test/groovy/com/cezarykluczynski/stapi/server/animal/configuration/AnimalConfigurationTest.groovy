package com.cezarykluczynski.stapi.server.animal.configuration

import com.cezarykluczynski.stapi.server.animal.endpoint.AnimalRestEndpoint
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class AnimalConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private AnimalConfiguration animalConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		animalConfiguration = new AnimalConfiguration(endpointFactory: endpointFactoryMock)
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
