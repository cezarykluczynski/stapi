package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialHeader
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MaterialHeaderRestMapperTest extends AbstractMaterialMapperTest {

	private MaterialHeaderRestMapper materialHeaderRestMapper

	void setup() {
		materialHeaderRestMapper = Mappers.getMapper(MaterialHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Material material = new Material(
				uid: UID,
				name: NAME)

		when:
		MaterialHeader materialHeader = materialHeaderRestMapper.map(Lists.newArrayList(material))[0]

		then:
		materialHeader.uid == UID
		materialHeader.name == NAME
	}

}
