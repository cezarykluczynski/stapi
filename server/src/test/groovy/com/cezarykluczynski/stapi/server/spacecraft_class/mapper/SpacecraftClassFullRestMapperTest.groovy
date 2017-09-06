package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFull
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

}
