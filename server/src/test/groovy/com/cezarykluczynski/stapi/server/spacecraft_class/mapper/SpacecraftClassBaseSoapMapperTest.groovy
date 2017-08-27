package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBase as SpacecraftClassBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftClassBaseSoapMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper

	void setup() {
		spacecraftClassBaseSoapMapper = Mappers.getMapper(SpacecraftClassBaseSoapMapper)
	}

	void "maps SOAP SpacecraftClassRequest to SpacecraftClassRequestDTO"() {
		given:
		SpacecraftClassBaseRequest spacecraftClassBaseRequest = new SpacecraftClassBaseRequest(
				name: NAME,
				warpCapable: WARP_CAPABLE,
				alternateReality: ALTERNATE_REALITY)

		when:
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassBaseSoapMapper.mapBase spacecraftClassBaseRequest

		then:
		spacecraftClassRequestDTO.name == NAME
		spacecraftClassRequestDTO.warpCapable == WARP_CAPABLE
		spacecraftClassRequestDTO.alternateReality == ALTERNATE_REALITY
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassBase spacecraftClassBase = spacecraftClassBaseSoapMapper.mapBase(Lists.newArrayList(spacecraftClass))[0]

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
