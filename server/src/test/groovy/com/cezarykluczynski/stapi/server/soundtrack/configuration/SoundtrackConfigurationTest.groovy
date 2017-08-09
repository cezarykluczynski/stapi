package com.cezarykluczynski.stapi.server.soundtrack.configuration

import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackSoapEndpoint
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseSoapMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SoundtrackConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SoundtrackConfiguration soundtrackConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		soundtrackConfiguration = new SoundtrackConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Soundtrack SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = soundtrackConfiguration.soundtrackEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SoundtrackSoapEndpoint, SoundtrackSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Soundtrack REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = soundtrackConfiguration.soundtrackServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SoundtrackRestEndpoint, SoundtrackRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SoundtrackBaseSoapMapper is created"() {
		when:
		SoundtrackBaseSoapMapper soundtrackBaseSoapMapper = soundtrackConfiguration.soundtrackBaseSoapMapper()

		then:
		soundtrackBaseSoapMapper != null
	}

	void "SoundtrackFullSoapMapper is created"() {
		when:
		SoundtrackFullSoapMapper soundtrackFullSoapMapper = soundtrackConfiguration.soundtrackFullSoapMapper()

		then:
		soundtrackFullSoapMapper != null
	}

	void "SoundtrackBaseRestMapper is created"() {
		when:
		SoundtrackBaseRestMapper soundtrackBaseRestMapper = soundtrackConfiguration.soundtrackBaseRestMapper()

		then:
		soundtrackBaseRestMapper != null
	}

	void "SoundtrackFullRestMapper is created"() {
		when:
		SoundtrackFullRestMapper soundtrackFullRestMapper = soundtrackConfiguration.soundtrackFullRestMapper()

		then:
		soundtrackFullRestMapper != null
	}

}
