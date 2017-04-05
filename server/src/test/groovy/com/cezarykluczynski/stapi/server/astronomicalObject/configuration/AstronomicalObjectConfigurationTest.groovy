package com.cezarykluczynski.stapi.server.astronomicalObject.configuration

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import spock.lang.Specification

import javax.xml.ws.Endpoint

class AstronomicalObjectConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private AstronomicalObjectConfiguration astronomicalObjectConfiguration

	void setup() {
		endpointFactoryMock = Mock(EndpointFactory)
		astronomicalObjectConfiguration = new AstronomicalObjectConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "AstronomicalObject SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock(Endpoint)

		when:
		Endpoint endpointOutput = astronomicalObjectConfiguration.astronomicalObjectEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(AstronomicalObjectSoapEndpoint, AstronomicalObjectSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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
