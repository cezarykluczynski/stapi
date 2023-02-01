package com.cezarykluczynski.stapi.server.performer.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerRestEndpoint
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerV2RestEndpoint
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class PerformerConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private PerformerConfiguration performerConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		performerConfiguration = new PerformerConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Performer REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = performerConfiguration.performerServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PerformerRestEndpoint, PerformerRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Performer V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = performerConfiguration.performerV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(PerformerV2RestEndpoint, PerformerV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "PerformerBaseRestMapper is created"() {
		when:
		PerformerBaseRestMapper performerBaseRestMapper = performerConfiguration.performerBaseRestMapper()

		then:
		performerBaseRestMapper != null
	}

	void "PerformerFullRestMapper is created"() {
		when:
		PerformerFullRestMapper performerFullRestMapper = performerConfiguration.performerFullRestMapper()

		then:
		performerFullRestMapper != null
	}

}
