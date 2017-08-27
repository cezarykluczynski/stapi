package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftWriterTest extends Specification {

	private SpacecraftRepository planetRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private SpacecraftWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		planetWriterMock = new SpacecraftWriter(planetRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Spacecraft planet = new Spacecraft()
		List<Spacecraft> planetList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(planetList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Spacecraft) >> { args ->
			assert args[0][0] == planet
			planetList
		}
		1 * planetRepositoryMock.save(planetList)
		0 * _
	}

}
