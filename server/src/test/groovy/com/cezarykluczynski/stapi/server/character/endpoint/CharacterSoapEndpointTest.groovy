package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader
import spock.lang.Specification

class CharacterSoapEndpointTest extends Specification {

	private CharacterSoapReader characterSoapReaderMock

	private CharacterSoapEndpoint characterSoapEndpoint

	void setup() {
		characterSoapReaderMock = Mock()
		characterSoapEndpoint = new CharacterSoapEndpoint(characterSoapReaderMock)
	}

	void "passes base call to CharacterSoapReader"() {
		given:
		CharacterBaseRequest characterBaseRequest = Mock()
		CharacterBaseResponse characterBaseResponse = Mock()

		when:
		CharacterBaseResponse characterResponseResult = characterSoapEndpoint.getCharacterBase(characterBaseRequest)

		then:
		1 * characterSoapReaderMock.readBase(characterBaseRequest) >> characterBaseResponse
		characterResponseResult == characterBaseResponse
	}

	void "passes full call to CharacterSoapReader"() {
		given:
		CharacterFullRequest characterFullRequest = Mock()
		CharacterFullResponse characterFullResponse = Mock()

		when:
		CharacterFullResponse characterResponseResult = characterSoapEndpoint.getCharacterFull(characterFullRequest)

		then:
		1 * characterSoapReaderMock.readFull(characterFullRequest) >> characterFullResponse
		characterResponseResult == characterFullResponse
	}

}
