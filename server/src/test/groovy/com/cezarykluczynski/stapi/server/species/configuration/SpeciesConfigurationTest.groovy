package com.cezarykluczynski.stapi.server.species.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesV2RestEndpoint
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SpeciesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpeciesConfiguration speciesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		speciesConfiguration = new SpeciesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Species REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = speciesConfiguration.speciesServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpeciesRestEndpoint, SpeciesRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Species V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = speciesConfiguration.speciesV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpeciesV2RestEndpoint, SpeciesV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SpeciesBaseRestMapper is created"() {
		when:
		SpeciesBaseRestMapper speciesBaseRestMapper = speciesConfiguration.speciesBaseRestMapper()

		then:
		speciesBaseRestMapper != null
	}

	void "SpeciesFullRestMapper is created"() {
		when:
		SpeciesFullRestMapper speciesFullRestMapper = speciesConfiguration.speciesFullRestMapper()

		then:
		speciesFullRestMapper != null
	}

}
