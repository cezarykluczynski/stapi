package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor

import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SoundtrackWriterTest extends Specification {

	private SoundtrackRepository soundtrackRepositoryMock

	private SoundtrackWriter soundtrackWriter

	void setup() {
		soundtrackRepositoryMock = Mock()
		soundtrackWriter = new SoundtrackWriter(soundtrackRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Soundtrack soundtrack = new Soundtrack()
		List<Soundtrack> soundtrackList = Lists.newArrayList(soundtrack)

		when:
		soundtrackWriter.write(new Chunk(soundtrackList))

		then:
		1 * soundtrackRepositoryMock.saveAll(soundtrackList)
		0 * _
	}

}
