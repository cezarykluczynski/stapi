package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
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
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(astronomicalObjectList))

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, AstronomicalObject) >> { args ->
			assert args[0][0] == planet
			astronomicalObjectList
		}
		1 * planetRepositoryMock.saveAll(astronomicalObjectList)
		0 * _
	}

}
