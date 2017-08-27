package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBase
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams
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
				warpCapable: WARP_CAPABLE,
				alternateReality: ALTERNATE_REALITY)

		when:
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseRestMapper.mapBase spacecraftClassRestBeanParams

		then:
		spacecraftClassRequestDTO.uid == UID
		spacecraftClassRequestDTO.name == NAME
		spacecraftClassRequestDTO.warpCapable == WARP_CAPABLE
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
		spacecraftClassBase.warpCapable == WARP_CAPABLE
		spacecraftClassBase.alternateReality == ALTERNATE_REALITY
		spacecraftClassBase.activeFrom == ACTIVE_FROM
		spacecraftClassBase.activeTo == ACTIVE_TO
		spacecraftClassBase.species != null
		spacecraftClassBase.owner != null
		spacecraftClassBase.operator != null
		spacecraftClassBase.affiliation != null
	}

}
