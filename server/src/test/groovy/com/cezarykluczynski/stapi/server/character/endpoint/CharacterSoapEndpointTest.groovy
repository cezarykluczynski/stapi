package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader
import spock.lang.Specification

class CharacterSoapEndpointTest extends Specification {

	private CharacterSoapReader characterSoapReaderMock

	private CharacterSoapEndpoint characterSoapEndpoint

	void setup() {
		characterSoapReaderMock = Mock(CharacterSoapReader)
		characterSoapEndpoint = new CharacterSoapEndpoint(characterSoapReaderMock)
	}

	void "passes call to CharacterSoapReader"() {
		given:
		CharacterRequest characterRequest = Mock(CharacterRequest)
		CharacterResponse characterResponse = Mock(CharacterResponse)

		when:
		CharacterResponse characterResponseResult = characterSoapEndpoint.getCharacters(characterRequest)

		then:
		1 * characterSoapReaderMock.readBase(characterRequest) >> characterResponse
		characterResponseResult == characterResponse
	}

}
