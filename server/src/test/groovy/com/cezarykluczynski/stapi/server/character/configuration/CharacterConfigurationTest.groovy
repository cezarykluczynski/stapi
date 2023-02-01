package com.cezarykluczynski.stapi.server.character.configuration

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class CharacterConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private CharacterConfiguration characterConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		characterConfiguration = new CharacterConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Character REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = characterConfiguration.characterServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(CharacterRestEndpoint, CharacterRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "CharacterBaseRestMapper is created"() {
		when:
		CharacterBaseRestMapper characterBaseRestMapper = characterConfiguration.characterBaseRestMapper()

		then:
		characterBaseRestMapper != null
	}

	void "CharacterFullRestMapper is created"() {
		when:
		CharacterFullRestMapper characterFullRestMapper = characterConfiguration.characterFullRestMapper()

		then:
		characterFullRestMapper != null
	}

}
