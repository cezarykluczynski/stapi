package com.cezarykluczynski.stapi.server.technology.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyRestEndpoint
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyV2RestEndpoint
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class TechnologyConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TechnologyConfiguration technologyConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		technologyConfiguration = new TechnologyConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Technology REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = technologyConfiguration.technologyServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TechnologyRestEndpoint, TechnologyRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Technology V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = technologyConfiguration.technologyV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TechnologyV2RestEndpoint, TechnologyV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "TechnologyBaseRestMapper is created"() {
		when:
		TechnologyBaseRestMapper technologyBaseRestMapper = technologyConfiguration.technologyBaseRestMapper()

		then:
		technologyBaseRestMapper != null
	}

	void "TechnologyFullRestMapper is created"() {
		when:
		TechnologyFullRestMapper technologyFullRestMapper = technologyConfiguration.technologyFullRestMapper()

		then:
		technologyFullRestMapper != null
	}

}
