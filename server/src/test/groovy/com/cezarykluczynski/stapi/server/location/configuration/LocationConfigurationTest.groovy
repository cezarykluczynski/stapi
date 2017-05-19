package com.cezarykluczynski.stapi.server.location.configuration

import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.location.endpoint.LocationSoapEndpoint
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class LocationConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private LocationConfiguration locationConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		locationConfiguration = new LocationConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Location SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = locationConfiguration.locationEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(LocationSoapEndpoint, LocationSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "LocationBaseSoapMapper is created"() {
		when:
		LocationBaseSoapMapper locationBaseSoapMapper = locationConfiguration.locationBaseSoapMapper()

		then:
		locationBaseSoapMapper != null
	}

	void "LocationFullSoapMapper is created"() {
		when:
		LocationFullSoapMapper locationFullSoapMapper = locationConfiguration.locationFullSoapMapper()

		then:
		locationFullSoapMapper != null
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
