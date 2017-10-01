package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFull
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import org.mapstruct.factory.Mappers

class SpacecraftFullRestMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftFullRestMapper spacecraftFullRestMapper

	void setup() {
		spacecraftFullRestMapper = Mappers.getMapper(SpacecraftFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftFull spacecraftFull = spacecraftFullRestMapper.mapFull(spacecraft)

		then:
		spacecraftFull.uid == UID
		spacecraftFull.name == NAME
		spacecraftFull.registry == REGISTRY
		spacecraftFull.status == STATUS
		spacecraftFull.dateStatus == DATE_STATUS
		spacecraftFull.spacecraftClass != null
		spacecraftFull.owner != null
		spacecraftFull.operator != null
		spacecraftFull.spacecraftTypes.size() == spacecraft.spacecraftTypes.size()
	}

}
