package com.cezarykluczynski.stapi.server.material.mapper

import com.cezarykluczynski.stapi.client.v1.soap.MaterialHeader
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class MaterialHeaderSoapMapperTest extends AbstractMaterialMapperTest {

	private MaterialHeaderSoapMapper materialHeaderSoapMapper

	void setup() {
		materialHeaderSoapMapper = Mappers.getMapper(MaterialHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		Material material = new Material(
				uid: UID,
				name: NAME)

		when:
		MaterialHeader materialHeader = materialHeaderSoapMapper.map(Lists.newArrayList(material))[0]

		then:
		materialHeader.uid == UID
		materialHeader.name == NAME
	}

}
