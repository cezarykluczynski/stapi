package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassBase
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2Base
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftClassBaseRestMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper

	void setup() {
		spacecraftClassBaseRestMapper = Mappers.getMapper(SpacecraftClassBaseRestMapper)
	}

	void "maps SpacecraftClassRestBeanParams to SpacecraftClassRequestDTO"() {
		given:
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = new SpacecraftClassRestBeanParams(
				uid: UID,
				name: NAME,
				warpCapable: WARP_CAPABLE_SPECIES,
				alternateReality: ALTERNATE_REALITY)

		when:
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseRestMapper.mapBase spacecraftClassRestBeanParams

		then:
		spacecraftClassRequestDTO.uid == UID
		spacecraftClassRequestDTO.name == NAME
		spacecraftClassRequestDTO.warpCapable == WARP_CAPABLE_SPECIES
		spacecraftClassRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps SpacecraftClassV2RestBeanParams to SpacecraftClassRequestDTO"() {
		given:
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = new SpacecraftClassV2RestBeanParams(
				uid: UID,
				name: NAME,
				warpCapable: WARP_CAPABLE_SPECIES,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)

		when:
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseRestMapper.mapV2Base spacecraftClassV2RestBeanParams

		then:
		spacecraftClassRequestDTO.uid == UID
		spacecraftClassRequestDTO.name == NAME
		spacecraftClassRequestDTO.warpCapable == WARP_CAPABLE_SPECIES
		spacecraftClassRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base REST entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassBase spacecraftClassBase = spacecraftClassBaseRestMapper.mapBase(Lists.newArrayList(spacecraftClass))[0]

		then:
		spacecraftClassBase.uid == UID
		spacecraftClassBase.name == NAME
		spacecraftClassBase.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClassBase.warpCapable == WARP_CAPABLE_SPECIES
		spacecraftClassBase.alternateReality == ALTERNATE_REALITY
		spacecraftClassBase.activeFrom == ACTIVE_FROM
		spacecraftClassBase.activeTo == ACTIVE_TO
		spacecraftClassBase.species != null
		spacecraftClassBase.owner != null
		spacecraftClassBase.operator != null
		spacecraftClassBase.affiliation != null
	}

	void "maps DB entity to base REST V2 entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassV2Base spacecraftClassBase = spacecraftClassBaseRestMapper.mapV2Base(Lists.newArrayList(spacecraftClass))[0]

		then:
		spacecraftClassBase.uid == UID
		spacecraftClassBase.name == NAME
		spacecraftClassBase.numberOfDecks == NUMBER_OF_DECKS
		spacecraftClassBase.crew == CREW
		spacecraftClassBase.warpCapable == WARP_CAPABLE_SPECIES
		spacecraftClassBase.mirror == MIRROR
		spacecraftClassBase.alternateReality == ALTERNATE_REALITY
		spacecraftClassBase.activeFrom == ACTIVE_FROM
		spacecraftClassBase.activeTo == ACTIVE_TO
		spacecraftClassBase.species != null
	}

}
