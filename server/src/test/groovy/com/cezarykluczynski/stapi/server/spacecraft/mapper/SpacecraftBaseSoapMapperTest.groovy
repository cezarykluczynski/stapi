package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBase as SpacecraftBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftBaseSoapMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftBaseSoapMapper spacecraftBaseSoapMapper

	void setup() {
		spacecraftBaseSoapMapper = Mappers.getMapper(SpacecraftBaseSoapMapper)
	}

	void "maps SOAP SpacecraftRequest to SpacecraftRequestDTO"() {
		given:
		SpacecraftBaseRequest spacecraftBaseRequest = new SpacecraftBaseRequest(name: NAME)

		when:
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftBaseSoapMapper.mapBase spacecraftBaseRequest

		then:
		spacecraftRequestDTO.name == NAME
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftBase spacecraftBase = spacecraftBaseSoapMapper.mapBase(Lists.newArrayList(spacecraft))[0]

		then:
		spacecraftBase.uid == UID
		spacecraftBase.name == NAME
		spacecraftBase.registry == REGISTRY
		spacecraftBase.status == STATUS
		spacecraftBase.dateStatus == DATE_STATUS
		spacecraftBase.spacecraftClass != null
		spacecraftBase.owner != null
		spacecraftBase.operator != null
	}

}
