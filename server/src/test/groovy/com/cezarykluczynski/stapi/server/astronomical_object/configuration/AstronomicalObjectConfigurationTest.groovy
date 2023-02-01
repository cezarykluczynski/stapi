package com.cezarykluczynski.stapi.server.astronomical_object.configuration

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectV2RestEndpoint
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class AstronomicalObjectConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private AstronomicalObjectConfiguration astronomicalObjectConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		astronomicalObjectConfiguration = new AstronomicalObjectConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "AstronomicalObject REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = astronomicalObjectConfiguration.astronomicalObjectServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(AstronomicalObjectRestEndpoint, AstronomicalObjectRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "AstronomicalObject V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = astronomicalObjectConfiguration.astronomicalObjectV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(AstronomicalObjectV2RestEndpoint, AstronomicalObjectV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "AstronomicalObjectBaseRestMapper is created"() {
		when:
		AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper = astronomicalObjectConfiguration.astronomicalObjectBaseRestMapper()

		then:
		astronomicalObjectBaseRestMapper != null
	}

	void "AstronomicalObjectFullRestMapper is created"() {
		when:
		AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper = astronomicalObjectConfiguration.astronomicalObjectFullRestMapper()

		then:
		astronomicalObjectFullRestMapper != null
	}

}
