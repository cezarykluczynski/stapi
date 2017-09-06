package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFull
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import org.mapstruct.factory.Mappers

class SpacecraftClassFullSoapMapperTest extends AbstractSpacecraftClassMapperTest {

	private SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper

	void setup() {
		spacecraftClassFullSoapMapper = Mappers.getMapper(SpacecraftClassFullSoapMapper)
	}

	void "maps SOAP SpacecraftClassFullRequest to SpacecraftClassBaseRequestDTO"() {
		given:
		SpacecraftClassFullRequest spacecraftClassFullRequest = new SpacecraftClassFullRequest(uid: UID)

		when:
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = spacecraftClassFullSoapMapper.mapFull spacecraftClassFullRequest

		then:
		spacecraftClassRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		SpacecraftClass spacecraftClass = createSpacecraftClass()

		when:
		SpacecraftClassFull spacecraftClassFull = spacecraftClassFullSoapMapper.mapFull(spacecraftClass)

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
