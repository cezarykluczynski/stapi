package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpacecraftClassWriterTest extends Specification {

	private SpacecraftClassRepository planetRepositoryMock

	private SpacecraftClassWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		planetWriterMock = new SpacecraftClassWriter(planetRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		SpacecraftClass planet = new SpacecraftClass()
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(spacecraftClassList))

		then:
		1 * planetRepositoryMock.saveAll(spacecraftClassList)
		0 * _
	}

}
