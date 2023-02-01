package com.cezarykluczynski.stapi.server.spacecraft_class.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassRestEndpoint
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassV2RestEndpoint
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SpacecraftClassConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpacecraftClassConfiguration spacecraftClassConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		spacecraftClassConfiguration = new SpacecraftClassConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "SpacecraftClass REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = spacecraftClassConfiguration.spacecraftClassServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpacecraftClassRestEndpoint, SpacecraftClassRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SpacecraftClass V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = spacecraftClassConfiguration.spacecraftClassV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpacecraftClassV2RestEndpoint, SpacecraftClassV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SpacecraftClassBaseRestMapper is created"() {
		when:
		SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper = spacecraftClassConfiguration.spacecraftClassBaseRestMapper()

		then:
		spacecraftClassBaseRestMapper != null
	}

	void "SpacecraftClassFullRestMapper is created"() {
		when:
		SpacecraftClassFullRestMapper spacecraftClassFullRestMapper = spacecraftClassConfiguration.spacecraftClassFullRestMapper()

		then:
		spacecraftClassFullRestMapper != null
	}

}
