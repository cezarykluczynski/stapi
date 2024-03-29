package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassFull
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2Full
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV3Full
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
		spacecraftClassFull.warpCapable == WARP_CAPABLE_SPECIES
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
		spacecraftClassV2Full.warpCapable == WARP_CAPABLE_SPECIES
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

	void "maps DB entity to full REST V3 entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassV3Full spacecraftClassV3Full = spacecraftClassFullRestMapper.mapV3Full(spacecraftClass)

		then:
		spacecraftClassV3Full.uid == UID
		spacecraftClassV3Full.name == NAME
		spacecraftClassV3Full.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClassV3Full.crew == CREW
		spacecraftClassV3Full.warpCapable == WARP_CAPABLE_SPECIES
		spacecraftClassV3Full.mirror == MIRROR
		spacecraftClassV3Full.alternateReality == ALTERNATE_REALITY
		spacecraftClassV3Full.activeFrom == ACTIVE_FROM
		spacecraftClassV3Full.activeTo == ACTIVE_TO
		spacecraftClassV3Full.species != null
		spacecraftClassV3Full.owners.size() == spacecraftClass.owners.size()
		spacecraftClassV3Full.operators.size() == spacecraftClass.operators.size()
		spacecraftClassV3Full.affiliations.size() == spacecraftClass.affiliations.size()
		spacecraftClassV3Full.spacecraftTypes.size() == spacecraftClass.spacecraftTypes.size()
		spacecraftClassV3Full.armaments.size() == spacecraftClass.armaments.size()
		spacecraftClassV3Full.defenses.size() == spacecraftClass.defenses.size()
		spacecraftClassV3Full.spacecrafts.size() == spacecraftClass.spacecrafts.size()
	}

}
