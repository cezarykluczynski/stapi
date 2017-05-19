package com.cezarykluczynski.stapi.server.character.configuration

import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterSoapEndpoint
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class CharacterConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private CharacterConfiguration characterConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		characterConfiguration = new CharacterConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Character SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = characterConfiguration.characterEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(CharacterSoapEndpoint, CharacterSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "CharacterBaseSoapMapper is created"() {
		when:
		CharacterBaseSoapMapper characterBaseSoapMapper = characterConfiguration.characterBaseSoapMapper()

		then:
		characterBaseSoapMapper != null
	}

	void "CharacterFullSoapMapper is created"() {
		when:
		CharacterFullSoapMapper characterFullSoapMapper = characterConfiguration.characterFullSoapMapper()

		then:
		characterFullSoapMapper != null
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
