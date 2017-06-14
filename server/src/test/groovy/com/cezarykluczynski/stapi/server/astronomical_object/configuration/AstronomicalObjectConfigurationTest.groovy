package com.cezarykluczynski.stapi.server.astronomical_object.configuration

import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectSoapEndpoint
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullSoapMapper
import com.cezarykluczynski.stapi.server.astronomical_object.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class AstronomicalObjectConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private AstronomicalObjectConfiguration astronomicalObjectConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		astronomicalObjectConfiguration = new AstronomicalObjectConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "AstronomicalObject SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = astronomicalObjectConfiguration.astronomicalObjectEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(AstronomicalObjectSoapEndpoint, AstronomicalObjectSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "AstronomicalObjectBaseSoapMapper is created"() {
		when:
		AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper = astronomicalObjectConfiguration.astronomicalObjectBaseSoapMapper()

		then:
		astronomicalObjectBaseSoapMapper != null
	}

	void "AstronomicalObjectFullSoapMapper is created"() {
		when:
		AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper = astronomicalObjectConfiguration.astronomicalObjectFullSoapMapper()

		then:
		astronomicalObjectFullSoapMapper != null
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
