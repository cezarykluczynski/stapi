package com.cezarykluczynski.stapi.server.spacecraft.configuration

import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftSoapEndpoint
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SpacecraftConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpacecraftConfiguration spacecraftConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		spacecraftConfiguration = new SpacecraftConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Spacecraft SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = spacecraftConfiguration.spacecraftEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SpacecraftSoapEndpoint, SpacecraftSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Spacecraft REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = spacecraftConfiguration.spacecraftServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpacecraftRestEndpoint, SpacecraftRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SpacecraftBaseSoapMapper is created"() {
		when:
		SpacecraftBaseSoapMapper spacecraftBaseSoapMapper = spacecraftConfiguration.spacecraftBaseSoapMapper()

		then:
		spacecraftBaseSoapMapper != null
	}

	void "SpacecraftFullSoapMapper is created"() {
		when:
		SpacecraftFullSoapMapper spacecraftFullSoapMapper = spacecraftConfiguration.spacecraftFullSoapMapper()

		then:
		spacecraftFullSoapMapper != null
	}

	void "SpacecraftBaseRestMapper is created"() {
		when:
		SpacecraftBaseRestMapper spacecraftBaseRestMapper = spacecraftConfiguration.spacecraftBaseRestMapper()

		then:
		spacecraftBaseRestMapper != null
	}

	void "SpacecraftFullRestMapper is created"() {
		when:
		SpacecraftFullRestMapper spacecraftFullRestMapper = spacecraftConfiguration.spacecraftFullRestMapper()

		then:
		spacecraftFullRestMapper != null
	}

}
