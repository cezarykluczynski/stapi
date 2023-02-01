package com.cezarykluczynski.stapi.server.occupation.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationRestEndpoint
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationV2RestEndpoint
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class OccupationConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private OccupationConfiguration occupationConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		occupationConfiguration = new OccupationConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Occupation REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = occupationConfiguration.occupationServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(OccupationRestEndpoint, OccupationRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Occupation V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = occupationConfiguration.occupationV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(OccupationV2RestEndpoint, OccupationV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "OccupationBaseRestMapper is created"() {
		when:
		OccupationBaseRestMapper occupationBaseRestMapper = occupationConfiguration.occupationBaseRestMapper()

		then:
		occupationBaseRestMapper != null
	}

	void "OccupationFullRestMapper is created"() {
		when:
		OccupationFullRestMapper occupationFullRestMapper = occupationConfiguration.occupationFullRestMapper()

		then:
		occupationFullRestMapper != null
	}

}
