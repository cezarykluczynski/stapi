package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class CharacterWriterTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private CharacterWriter characterWriterMock

	void setup() {
		characterRepositoryMock = Mock()
		characterWriterMock = new CharacterWriter(characterRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Character character = new Character()
		List<Character> characterList = Lists.newArrayList(character)

		when:
		characterWriterMock.write(new Chunk(characterList))

		then:
		1 * characterRepositoryMock.saveAll(characterList)
		0 * _
	}

}
