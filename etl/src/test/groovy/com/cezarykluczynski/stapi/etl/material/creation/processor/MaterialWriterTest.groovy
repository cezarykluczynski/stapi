package com.cezarykluczynski.stapi.etl.material.creation.processor

import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class MaterialWriterTest extends Specification {

	private MaterialRepository materialRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private MaterialWriter materialWriterMock

	void setup() {
		materialRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		materialWriterMock = new MaterialWriter(materialRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Material material = new Material()
		List<Material> materialList = Lists.newArrayList(material)

		when:
		materialWriterMock.write(materialList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Material) >> { args ->
			assert args[0][0] == material
			materialList
		}
		1 * materialRepositoryMock.save(materialList)
		0 * _
	}

}
