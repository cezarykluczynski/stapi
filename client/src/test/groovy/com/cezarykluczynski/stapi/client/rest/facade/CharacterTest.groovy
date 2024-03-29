package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.CharacterApi
import com.cezarykluczynski.stapi.client.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.client.rest.model.CharacterSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.Gender
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
		1 * characterApiMock.v1GetCharacter(UID) >> characterFullResponse
		0 * _
		characterFullResponse == characterFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		CharacterBaseResponse characterBaseResponse = Mock()
		CharacterSearchCriteria characterSearchCriteria = new CharacterSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				gender: Gender.valueOf(GENDER),
				deceased: DECEASED,
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		characterSearchCriteria.sort = SORT

		when:
		CharacterBaseResponse characterBaseResponseOutput = character.search(characterSearchCriteria)

		then:
		1 * characterApiMock.v1SearchCharacters(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, GENDER, DECEASED, HOLOGRAM,
				FICTIONAL_CHARACTER, MIRROR, ALTERNATE_REALITY) >> characterBaseResponse
		0 * _
		characterBaseResponse == characterBaseResponseOutput
	}

}
