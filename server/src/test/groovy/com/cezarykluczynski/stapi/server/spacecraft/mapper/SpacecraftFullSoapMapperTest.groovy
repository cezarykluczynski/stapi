package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFull
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import org.mapstruct.factory.Mappers

class SpacecraftFullSoapMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftFullSoapMapper spacecraftFullSoapMapper

	void setup() {
		spacecraftFullSoapMapper = Mappers.getMapper(SpacecraftFullSoapMapper)
	}

	void "maps SOAP SpacecraftFullRequest to SpacecraftBaseRequestDTO"() {
		given:
		SpacecraftFullRequest spacecraftFullRequest = new SpacecraftFullRequest(uid: UID)

		when:
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftFullSoapMapper.mapFull spacecraftFullRequest

		then:
		spacecraftRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftFull spacecraftFull = spacecraftFullSoapMapper.mapFull(spacecraft)

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
