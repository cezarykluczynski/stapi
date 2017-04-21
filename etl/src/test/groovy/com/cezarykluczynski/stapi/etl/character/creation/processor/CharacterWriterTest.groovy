package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterWriterTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private CharacterWriter characterWriterMock

	void setup() {
		characterRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		characterWriterMock = new CharacterWriter(characterRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Character character = new Character()
		List<Character> characterList = Lists.newArrayList(character)

		when:
		characterWriterMock.write(characterList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Character) >> { args ->
			assert args[0][0] == character
			characterList
		}
		1 * characterRepositoryMock.save(characterList)
		0 * _
	}

}
