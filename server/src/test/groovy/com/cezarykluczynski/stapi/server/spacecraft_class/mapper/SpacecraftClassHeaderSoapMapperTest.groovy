package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassHeader
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftClassHeaderSoapMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassHeaderSoapMapper spacecraftClassHeaderSoapMapper

	void setup() {
		spacecraftClassHeaderSoapMapper = Mappers.getMapper(SpacecraftClassHeaderSoapMapper)
	}

	void "maps DB entity to SOAP header"() {
		given:
		SpacecraftClass spacecraftClass = new SpacecraftClass(
				uid: UID,
				name: NAME)

		when:
		SpacecraftClassHeader spacecraftClassHeader = spacecraftClassHeaderSoapMapper.map(Lists.newArrayList(spacecraftClass))[0]

		then:
		spacecraftClassHeader.uid == UID
		spacecraftClassHeader.name == NAME
	}

}
