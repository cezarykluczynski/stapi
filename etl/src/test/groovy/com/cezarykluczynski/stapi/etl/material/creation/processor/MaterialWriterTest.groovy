package com.cezarykluczynski.stapi.etl.material.creation.processor

import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class MaterialWriterTest extends Specification {

	private MaterialRepository materialRepositoryMock

	private MaterialWriter materialWriterMock

	void setup() {
		materialRepositoryMock = Mock()
		materialWriterMock = new MaterialWriter(materialRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Material material = new Material()
		List<Material> materialList = Lists.newArrayList(material)

		when:
		materialWriterMock.write(new Chunk(materialList))

		then:
		1 * materialRepositoryMock.saveAll(materialList)
		0 * _
	}

}
