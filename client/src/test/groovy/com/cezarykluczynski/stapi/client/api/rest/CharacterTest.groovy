package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.CharacterApi
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.util.AbstractIndividualTest

class CharacterTest extends AbstractIndividualTest {

	private CharacterApi characterApiMock

	private Character character

	void setup() {
		characterApiMock = Mock()
		character = new Character(characterApiMock)
	}

	void "gets single entity"() {
		given:
		CharacterFullResponse characterFullResponse = Mock()

		when:
		CharacterFullResponse characterFullResponseOutput = character.get(UID)

		then:
		1 * characterApiMock.v1RestCharacterGet(UID, null) >> characterFullResponse
		0 * _
		characterFullResponse == characterFullResponseOutput
	}

	void "searches entities"() {
		given:
		CharacterBaseResponse characterBaseResponse = Mock()

		when:
		CharacterBaseResponse characterBaseResponseOutput = character.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, GENDER, DECEASED, HOLOGRAM,
				FICTIONAL_CHARACTER, MIRROR, ALTERNATE_REALITY)

		then:
		1 * characterApiMock.v1RestCharacterSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, GENDER, DECEASED, HOLOGRAM, FICTIONAL_CHARACTER, MIRROR,
				ALTERNATE_REALITY) >> characterBaseResponse
		0 * _
		characterBaseResponse == characterBaseResponseOutput
	}

}
