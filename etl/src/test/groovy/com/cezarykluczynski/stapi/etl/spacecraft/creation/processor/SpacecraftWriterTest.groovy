package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpacecraftWriterTest extends Specification {

	private SpacecraftRepository planetRepositoryMock

	private SpacecraftWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		planetWriterMock = new SpacecraftWriter(planetRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Spacecraft planet = new Spacecraft()
		List<Spacecraft> spacecraftList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(spacecraftList))

		then:
		1 * planetRepositoryMock.saveAll(spacecraftList)
		0 * _
	}

}
