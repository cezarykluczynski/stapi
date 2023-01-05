package com.cezarykluczynski.stapi.etl.character.link.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class CharacterLinkWriterTest extends Specification {

	private CharacterLinkWriter characterLinkWriter

	void setup() {
		characterLinkWriter = new CharacterLinkWriter()
	}

	void "does nothing"() {
		given:
		Chunk<Character> characterChunk = Mock()

		when:
		characterLinkWriter.write(characterChunk)

		then:
		0 * _
	}

}
