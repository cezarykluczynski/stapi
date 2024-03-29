package com.cezarykluczynski.stapi.server.title.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.title.endpoint.TitleRestEndpoint
import com.cezarykluczynski.stapi.server.title.endpoint.TitleV2RestEndpoint
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class TitleConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TitleConfiguration titleConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		titleConfiguration = new TitleConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Title REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = titleConfiguration.titleServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TitleRestEndpoint, TitleRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Title V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = titleConfiguration.titleV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(TitleV2RestEndpoint, TitleV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "TitleBaseRestMapper is created"() {
		when:
		TitleBaseRestMapper titleBaseRestMapper = titleConfiguration.titleBaseRestMapper()

		then:
		titleBaseRestMapper != null
	}

	void "TitleFullRestMapper is created"() {
		when:
		TitleFullRestMapper titleFullRestMapper = titleConfiguration.titleFullRestMapper()

		then:
		titleFullRestMapper != null
	}

}
