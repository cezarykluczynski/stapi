package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class CharacterLinkWriterTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private CharacterLinkWriter characterLinkWriter

	void setup() {
		characterRepositoryMock = Mock()
		characterLinkWriter = new CharacterLinkWriter(characterRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Character character = new Character()
		List<Character> characterList = Lists.newArrayList(character)

		when:
		characterLinkWriter.write(new Chunk(characterList))

		then:
		1 * characterRepositoryMock.saveAll(characterList)
		0 * _
	}

}
