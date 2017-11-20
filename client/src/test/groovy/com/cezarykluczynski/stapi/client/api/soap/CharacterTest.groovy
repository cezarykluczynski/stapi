package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType
import spock.lang.Specification

class CharacterTest extends Specification {

	private CharacterPortType characterPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private Character character

	void setup() {
		characterPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		character = new Character(characterPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		CharacterBaseRequest characterBaseRequest = Mock()
		CharacterBaseResponse characterBaseResponse = Mock()

		when:
		CharacterBaseResponse characterBaseResponseOutput = character.search(characterBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(characterBaseRequest)
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
		1 * apiKeySupplierMock.supply(characterFullRequest)
		1 * characterPortTypeMock.getCharacterFull(characterFullRequest) >> characterFullResponse
		0 * _
		characterFullResponse == characterFullResponseOutput
	}

}
