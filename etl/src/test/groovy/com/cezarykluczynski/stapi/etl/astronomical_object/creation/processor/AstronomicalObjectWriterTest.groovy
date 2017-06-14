package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class AstronomicalObjectWriterTest extends Specification {

	private AstronomicalObjectRepository planetRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private AstronomicalObjectWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		planetWriterMock = new AstronomicalObjectWriter(planetRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		AstronomicalObject planet = new AstronomicalObject()
		List<AstronomicalObject> planetList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(planetList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, AstronomicalObject) >> { args ->
			assert args[0][0] == planet
			planetList
		}
		1 * planetRepositoryMock.save(planetList)
		0 * _
	}

}
