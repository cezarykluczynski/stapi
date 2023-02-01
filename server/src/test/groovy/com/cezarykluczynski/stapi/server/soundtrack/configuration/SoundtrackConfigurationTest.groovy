package com.cezarykluczynski.stapi.server.soundtrack.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.soundtrack.endpoint.SoundtrackRestEndpoint
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackBaseRestMapper
import com.cezarykluczynski.stapi.server.soundtrack.mapper.SoundtrackFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SoundtrackConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SoundtrackConfiguration soundtrackConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		soundtrackConfiguration = new SoundtrackConfiguration(endpointFactory: endpointFactoryMock)
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
