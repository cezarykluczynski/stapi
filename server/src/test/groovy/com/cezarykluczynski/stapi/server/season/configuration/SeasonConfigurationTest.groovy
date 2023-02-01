package com.cezarykluczynski.stapi.server.season.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.season.endpoint.SeasonRestEndpoint
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SeasonConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SeasonConfiguration seasonConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		seasonConfiguration = new SeasonConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Season REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = seasonConfiguration.seasonServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SeasonRestEndpoint, SeasonRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SeasonBaseRestMapper is created"() {
		when:
		SeasonBaseRestMapper seasonBaseRestMapper = seasonConfiguration.seasonBaseRestMapper()

		then:
		seasonBaseRestMapper != null
	}

	void "SeasonFullRestMapper is created"() {
		when:
		SeasonFullRestMapper seasonFullRestMapper = seasonConfiguration.seasonFullRestMapper()

		then:
		seasonFullRestMapper != null
	}

}
