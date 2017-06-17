package com.cezarykluczynski.stapi.server.literature.configuration

import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.literature.endpoint.LiteratureSoapEndpoint
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class LiteratureConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private LiteratureConfiguration literatureConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		literatureConfiguration = new LiteratureConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Literature SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = literatureConfiguration.literatureEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(LiteratureSoapEndpoint, LiteratureSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "LiteratureBaseSoapMapper is created"() {
		when:
		LiteratureBaseSoapMapper literatureBaseSoapMapper = literatureConfiguration.literatureBaseSoapMapper()

		then:
		literatureBaseSoapMapper != null
	}

	void "LiteratureFullSoapMapper is created"() {
		when:
		LiteratureFullSoapMapper literatureFullSoapMapper = literatureConfiguration.literatureFullSoapMapper()

		then:
		literatureFullSoapMapper != null
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
