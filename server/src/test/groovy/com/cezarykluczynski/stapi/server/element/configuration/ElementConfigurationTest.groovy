package com.cezarykluczynski.stapi.server.element.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.element.endpoint.ElementRestEndpoint
import com.cezarykluczynski.stapi.server.element.endpoint.ElementV2RestEndpoint
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class ElementConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ElementConfiguration elementConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		elementConfiguration = new ElementConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Element REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = elementConfiguration.elementServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ElementRestEndpoint, ElementRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Element V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = elementConfiguration.elementV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ElementV2RestEndpoint, ElementV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ElementBaseRestMapper is created"() {
		when:
		ElementBaseRestMapper elementBaseRestMapper = elementConfiguration.elementBaseRestMapper()

		then:
		elementBaseRestMapper != null
	}

	void "ElementFullRestMapper is created"() {
		when:
		ElementFullRestMapper elementFullRestMapper = elementConfiguration.elementFullRestMapper()

		then:
		elementFullRestMapper != null
	}

}
