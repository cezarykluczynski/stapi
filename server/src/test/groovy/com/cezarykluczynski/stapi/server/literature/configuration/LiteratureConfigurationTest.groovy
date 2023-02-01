package com.cezarykluczynski.stapi.server.literature.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureRestEndpoint
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class LiteratureConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private LiteratureConfiguration literatureConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		literatureConfiguration = new LiteratureConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Literature REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = literatureConfiguration.literatureServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(LiteratureRestEndpoint, LiteratureRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "LiteratureBaseRestMapper is created"() {
		when:
		LiteratureBaseRestMapper literatureBaseRestMapper = literatureConfiguration.literatureBaseRestMapper()

		then:
		literatureBaseRestMapper != null
	}

	void "LiteratureFullRestMapper is created"() {
		when:
		LiteratureFullRestMapper literatureFullRestMapper = literatureConfiguration.literatureFullRestMapper()

		then:
		literatureFullRestMapper != null
	}

}
