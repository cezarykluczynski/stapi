package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftHeader
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftHeaderRestMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftHeaderRestMapper spacecraftHeaderRestMapper

	void setup() {
		spacecraftHeaderRestMapper = Mappers.getMapper(SpacecraftHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Spacecraft spacecraft = new Spacecraft(
				uid: UID,
				name: NAME)

		when:
		SpacecraftHeader spacecraftHeader = spacecraftHeaderRestMapper.map(Lists.newArrayList(spacecraft))[0]

		then:
		spacecraftHeader.uid == UID
		spacecraftHeader.name == NAME
	}

}
