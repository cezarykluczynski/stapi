package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassHeader
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftClassHeaderRestMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassHeaderRestMapper spacecraftClassHeaderRestMapper

	void setup() {
		spacecraftClassHeaderRestMapper = Mappers.getMapper(SpacecraftClassHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		SpacecraftClass spacecraftClass = new SpacecraftClass(
				uid: UID,
				name: NAME)

		when:
		SpacecraftClassHeader spacecraftClassHeader = spacecraftClassHeaderRestMapper.map(Lists.newArrayList(spacecraftClass))[0]

		then:
		spacecraftClassHeader.uid == UID
		spacecraftClassHeader.name == NAME
	}

}
