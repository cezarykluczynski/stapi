package com.cezarykluczynski.stapi.server.technology.configuration

import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologyRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.technology.endpoint.TechnologySoapEndpoint
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class TechnologyConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TechnologyConfiguration technologyConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		technologyConfiguration = new TechnologyConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Technology SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = technologyConfiguration.technologyEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(TechnologySoapEndpoint, TechnologySoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "TechnologyBaseSoapMapper is created"() {
		when:
		TechnologyBaseSoapMapper technologyBaseSoapMapper = technologyConfiguration.technologyBaseSoapMapper()

		then:
		technologyBaseSoapMapper != null
	}

	void "TechnologyFullSoapMapper is created"() {
		when:
		TechnologyFullSoapMapper technologyFullSoapMapper = technologyConfiguration.technologyFullSoapMapper()

		then:
		technologyFullSoapMapper != null
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
