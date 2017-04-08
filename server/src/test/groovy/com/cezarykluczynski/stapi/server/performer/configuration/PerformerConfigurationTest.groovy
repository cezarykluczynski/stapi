package com.cezarykluczynski.stapi.server.performer.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerSoapEndpoint
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper
import spock.lang.Specification

import javax.xml.ws.Endpoint

class PerformerConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private PerformerConfiguration performerConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		performerConfiguration = new PerformerConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Performer SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = performerConfiguration.performerEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(PerformerSoapEndpoint, PerformerSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "PerformerBaseSoapMapper is created"() {
		when:
		PerformerBaseSoapMapper performerBaseSoapMapper = performerConfiguration.performerBaseSoapMapper()

		then:
		performerBaseSoapMapper != null
	}

	void "PerformerFullSoapMapper is created"() {
		when:
		PerformerFullSoapMapper performerFullSoapMapper = performerConfiguration.performerFullSoapMapper()

		then:
		performerFullSoapMapper != null
	}

	void "PerformerBaseRestMapper is created"() {
		when:
		PerformerBaseRestMapper performerBaseRestMapper = performerConfiguration.performerBaseRestMapper()

		then:
		performerBaseRestMapper != null
	}

	void "PerformerFullRestMapper is created"() {
		when:
		PerformerFullRestMapper performerFullRestMapper = performerConfiguration.performerFullRestMapper()

		then:
		performerFullRestMapper != null
	}

}
