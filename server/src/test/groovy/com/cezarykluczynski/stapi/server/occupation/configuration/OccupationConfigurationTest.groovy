package com.cezarykluczynski.stapi.server.occupation.configuration

import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.occupation.endpoint.OccupationSoapEndpoint
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class OccupationConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private OccupationConfiguration occupationConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		occupationConfiguration = new OccupationConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Occupation SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = occupationConfiguration.occupationEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(OccupationSoapEndpoint, OccupationSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "OccupationBaseSoapMapper is created"() {
		when:
		OccupationBaseSoapMapper occupationBaseSoapMapper = occupationConfiguration.occupationBaseSoapMapper()

		then:
		occupationBaseSoapMapper != null
	}

	void "OccupationFullSoapMapper is created"() {
		when:
		OccupationFullSoapMapper occupationFullSoapMapper = occupationConfiguration.occupationFullSoapMapper()

		then:
		occupationFullSoapMapper != null
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
