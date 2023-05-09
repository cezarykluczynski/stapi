package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class AstronomicalObjectWriterTest extends Specification {

	private AstronomicalObjectRepository planetRepositoryMock

	private AstronomicalObjectWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		planetWriterMock = new AstronomicalObjectWriter(planetRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		AstronomicalObject planet = new AstronomicalObject()
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(astronomicalObjectList))

		then:
		1 * planetRepositoryMock.saveAll(astronomicalObjectList)
		0 * _
	}

}
