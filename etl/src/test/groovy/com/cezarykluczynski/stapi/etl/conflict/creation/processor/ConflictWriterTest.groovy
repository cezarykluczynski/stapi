package com.cezarykluczynski.stapi.etl.conflict.creation.processor

import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ConflictWriterTest extends Specification {

	private ConflictRepository planetRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ConflictWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		planetWriterMock = new ConflictWriter(planetRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Conflict planet = new Conflict()
		List<Conflict> planetList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(planetList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Conflict) >> { args ->
			assert args[0][0] == planet
			planetList
		}
		1 * planetRepositoryMock.save(planetList)
		0 * _
	}

}
