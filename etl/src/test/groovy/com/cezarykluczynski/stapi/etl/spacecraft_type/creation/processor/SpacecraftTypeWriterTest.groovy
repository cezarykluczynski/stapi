package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpacecraftTypeWriterTest extends Specification {

	private SpacecraftTypeRepository planetRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private SpacecraftTypeWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		planetWriterMock = new SpacecraftTypeWriter(planetRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		SpacecraftType planet = new SpacecraftType()
		List<SpacecraftType> spacecraftTypeList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(spacecraftTypeList))

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, SpacecraftType) >> { args ->
			assert args[0][0] == planet
			spacecraftTypeList
		}
		1 * planetRepositoryMock.saveAll(spacecraftTypeList)
		0 * _
	}

}
