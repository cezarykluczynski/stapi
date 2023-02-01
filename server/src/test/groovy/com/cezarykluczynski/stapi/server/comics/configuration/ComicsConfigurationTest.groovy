package com.cezarykluczynski.stapi.server.comics.configuration

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class ComicsConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicsConfiguration comicsConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicsConfiguration = new ComicsConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Comics REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = comicsConfiguration.comicsServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ComicsRestEndpoint, ComicsRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "ComicsBaseRestMapper is created"() {
		when:
		ComicsBaseRestMapper comicsBaseRestMapper = comicsConfiguration.comicsBaseRestMapper()

		then:
		comicsBaseRestMapper != null
	}

	void "ComicsFullRestMapper is created"() {
		when:
		ComicsFullRestMapper comicsFullRestMapper = comicsConfiguration.comicsFullRestMapper()

		then:
		comicsFullRestMapper != null
	}

}
