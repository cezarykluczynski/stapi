package com.cezarykluczynski.stapi.server.location.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint
import com.cezarykluczynski.stapi.server.location.endpoint.LocationV2RestEndpoint
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class LocationConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private LocationConfiguration locationConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		locationConfiguration = new LocationConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Location REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = locationConfiguration.locationServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(LocationRestEndpoint, LocationRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Location V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = locationConfiguration.locationV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(LocationV2RestEndpoint, LocationV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "LocationBaseRestMapper is created"() {
		when:
		LocationBaseRestMapper locationBaseRestMapper = locationConfiguration.locationBaseRestMapper()

		then:
		locationBaseRestMapper != null
	}

	void "LocationFullRestMapper is created"() {
		when:
		LocationFullRestMapper locationFullRestMapper = locationConfiguration.locationFullRestMapper()

		then:
		locationFullRestMapper != null
	}

}
