package com.cezarykluczynski.stapi.server.title.configuration

import com.cezarykluczynski.stapi.server.title.endpoint.TitleRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.title.endpoint.TitleSoapEndpoint
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullRestMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class TitleConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private TitleConfiguration titleConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		titleConfiguration = new TitleConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Title SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = titleConfiguration.titleEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(TitleSoapEndpoint, TitleSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "TitleBaseSoapMapper is created"() {
		when:
		TitleBaseSoapMapper titleBaseSoapMapper = titleConfiguration.titleBaseSoapMapper()

		then:
		titleBaseSoapMapper != null
	}

	void "TitleFullSoapMapper is created"() {
		when:
		TitleFullSoapMapper titleFullSoapMapper = titleConfiguration.titleFullSoapMapper()

		then:
		titleFullSoapMapper != null
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
