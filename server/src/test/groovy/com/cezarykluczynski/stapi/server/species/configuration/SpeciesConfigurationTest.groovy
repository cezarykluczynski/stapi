package com.cezarykluczynski.stapi.server.species.configuration

import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesSoapEndpoint
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullRestMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SpeciesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpeciesConfiguration speciesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		speciesConfiguration = new SpeciesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Species SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = speciesConfiguration.speciesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SpeciesSoapEndpoint, SpeciesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "SpeciesBaseSoapMapper is created"() {
		when:
		SpeciesBaseSoapMapper speciesBaseSoapMapper = speciesConfiguration.speciesBaseSoapMapper()

		then:
		speciesBaseSoapMapper != null
	}

	void "SpeciesFullSoapMapper is created"() {
		when:
		SpeciesFullSoapMapper speciesFullSoapMapper = speciesConfiguration.speciesFullSoapMapper()

		then:
		speciesFullSoapMapper != null
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
