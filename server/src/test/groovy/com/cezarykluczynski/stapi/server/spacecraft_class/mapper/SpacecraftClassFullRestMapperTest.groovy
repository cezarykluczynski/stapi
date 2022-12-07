package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFull
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2Full
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import org.mapstruct.factory.Mappers

class SpacecraftClassFullRestMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassFullRestMapper spacecraftClassFullRestMapper

	void setup() {
		spacecraftClassFullRestMapper = Mappers.getMapper(SpacecraftClassFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassFull spacecraftClassFull = spacecraftClassFullRestMapper.mapFull(spacecraftClass)

		then:
		spacecraftClassFull.uid == UID
		spacecraftClassFull.name == NAME
		spacecraftClassFull.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClassFull.warpCapable == WARP_CAPABLE
		spacecraftClassFull.alternateReality == ALTERNATE_REALITY
		spacecraftClassFull.activeFrom == ACTIVE_FROM
		spacecraftClassFull.activeTo == ACTIVE_TO
		spacecraftClassFull.species != null
		spacecraftClassFull.owner != null
		spacecraftClassFull.operator != null
		spacecraftClassFull.affiliation != null
		spacecraftClassFull.spacecraftTypes.size() == spacecraftClass.spacecraftTypes.size()
		spacecraftClassFull.spacecrafts.size() == spacecraftClass.spacecrafts.size()
	}

	void "maps DB entity to full REST V2 entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassV2Full spacecraftClassV2Full = spacecraftClassFullRestMapper.mapV2Full(spacecraftClass)

		then:
		spacecraftClassV2Full.uid == UID
		spacecraftClassV2Full.name == NAME
		spacecraftClassV2Full.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClassV2Full.crew == CREW
		spacecraftClassV2Full.warpCapable == WARP_CAPABLE
		spacecraftClassV2Full.mirror == MIRROR
		spacecraftClassV2Full.alternateReality == ALTERNATE_REALITY
		spacecraftClassV2Full.activeFrom == ACTIVE_FROM
		spacecraftClassV2Full.activeTo == ACTIVE_TO
		spacecraftClassV2Full.species != null
		spacecraftClassV2Full.owners.size() == spacecraftClass.owners.size()
		spacecraftClassV2Full.operators.size() == spacecraftClass.operators.size()
		spacecraftClassV2Full.affiliations.size() == spacecraftClass.affiliations.size()
		spacecraftClassV2Full.spacecraftTypes.size() == spacecraftClass.spacecraftTypes.size()
		spacecraftClassV2Full.armaments.size() == spacecraftClass.armaments.size()
		spacecraftClassV2Full.spacecrafts.size() == spacecraftClass.spacecrafts.size()
	}

}
