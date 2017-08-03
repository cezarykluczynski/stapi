package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor

import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class SoundtrackWriterTest extends Specification {

	private SoundtrackRepository soundtrackRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private SoundtrackWriter soundtrackWriter

	void setup() {
		soundtrackRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		soundtrackWriter = new SoundtrackWriter(soundtrackRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Soundtrack soundtrack = new Soundtrack()
		List<Soundtrack> soundtrackList = Lists.newArrayList(soundtrack)

		when:
		soundtrackWriter.write(soundtrackList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Soundtrack) >> { args ->
			assert args[0][0] == soundtrack
			soundtrackList
		}
		1 * soundtrackRepositoryMock.save(soundtrackList)
		0 * _
	}

}
