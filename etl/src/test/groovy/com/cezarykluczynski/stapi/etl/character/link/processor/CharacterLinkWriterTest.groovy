package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import spock.lang.Specification

class CharacterLinkWriterTest extends Specification {

	private CharacterLinkWriter characterLinkWriter

	void setup() {
		characterLinkWriter = new CharacterLinkWriter()
	}

	void "does nothing"() {
		given:
		List<Character> characterList = Mock()

		when:
		characterLinkWriter.write(characterList)

		then:
		0 * _
	}

}
