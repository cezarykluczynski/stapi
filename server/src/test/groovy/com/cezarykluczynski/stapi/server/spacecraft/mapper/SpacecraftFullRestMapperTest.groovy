package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftFull
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftV2Full spacecraftV2Full = spacecraftFullRestMapper.mapV2Full(spacecraft)

		then:
		spacecraftV2Full.uid == UID
		spacecraftV2Full.name == NAME
		spacecraftV2Full.registry == REGISTRY
		spacecraftV2Full.status == STATUS
		spacecraftV2Full.dateStatus == DATE_STATUS
		spacecraftV2Full.spacecraftClass != null
		spacecraftV2Full.owner != null
		spacecraftV2Full.operator != null
		spacecraftV2Full.affiliation != null
		spacecraftV2Full.spacecraftTypes.size() == spacecraft.spacecraftTypes.size()
	}

}
