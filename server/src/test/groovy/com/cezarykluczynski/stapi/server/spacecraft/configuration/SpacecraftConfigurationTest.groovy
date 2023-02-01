package com.cezarykluczynski.stapi.server.spacecraft.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftRestEndpoint
import com.cezarykluczynski.stapi.server.spacecraft.endpoint.SpacecraftV2RestEndpoint
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SpacecraftConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpacecraftConfiguration spacecraftConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		spacecraftConfiguration = new SpacecraftConfiguration(endpointFactory: endpointFactoryMock)
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

	void "Spacecraft V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = spacecraftConfiguration.spacecraftV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpacecraftV2RestEndpoint, SpacecraftV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
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
