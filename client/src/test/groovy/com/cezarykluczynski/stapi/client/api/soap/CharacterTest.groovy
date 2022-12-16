package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType
import spock.lang.Specification

class CharacterTest extends Specification {

	private CharacterPortType characterPortTypeMock

	private Character character

	void setup() {
		characterPortTypeMock = Mock()
		character = new Character(characterPortTypeMock)
	}

	void "gets single entity"() {
		given:
		CharacterBaseRequest characterBaseRequest = Mock()
		CharacterBaseResponse characterBaseResponse = Mock()

		when:
		CharacterBaseResponse characterBaseResponseOutput = character.search(characterBaseRequest)

		then:
		1 * characterPortTypeMock.getCharacterBase(characterBaseRequest) >> characterBaseResponse
		0 * _
		characterBaseResponse == characterBaseResponseOutput
	}

	void "searches entities"() {
		given:
		CharacterFullRequest characterFullRequest = Mock()
		CharacterFullResponse characterFullResponse = Mock()

		when:
		CharacterFullResponse characterFullResponseOutput = character.get(characterFullRequest)

		then:
		1 * characterPortTypeMock.getCharacterFull(characterFullRequest) >> characterFullResponse
		0 * _
		characterFullResponse == characterFullResponseOutput
	}

}
