package com.cezarykluczynski.stapi.server.common.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.CommonRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class CommonConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private CommonConfiguration commonConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		commonConfiguration = new CommonConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Common REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = commonConfiguration.commonServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(CommonRestEndpoint, CommonRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

}
