package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SpacecraftClassWriterTest extends Specification {

	private SpacecraftClassRepository planetRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private SpacecraftClassWriter planetWriterMock

	void setup() {
		planetRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		planetWriterMock = new SpacecraftClassWriter(planetRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		SpacecraftClass planet = new SpacecraftClass()
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList(planet)

		when:
		planetWriterMock.write(new Chunk(spacecraftClassList))

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, SpacecraftClass) >> { args ->
			assert args[0][0] == planet
			spacecraftClassList
		}
		1 * planetRepositoryMock.saveAll(spacecraftClassList)
		0 * _
	}

}
