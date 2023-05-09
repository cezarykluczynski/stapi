package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpacecraftTypeWriterTest extends Specification {

	private SpacecraftTypeRepository planetRepositoryMock

	private SpacecraftTypeWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		planetWriterMock = new SpacecraftTypeWriter(planetRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		SpacecraftType planet = new SpacecraftType()
		List<SpacecraftType> spacecraftTypeList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(spacecraftTypeList))

		then:
		1 * planetRepositoryMock.saveAll(spacecraftTypeList)
		0 * _
	}

}
