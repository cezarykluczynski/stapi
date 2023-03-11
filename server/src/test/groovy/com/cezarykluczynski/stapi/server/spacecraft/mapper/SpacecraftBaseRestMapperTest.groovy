package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftBase
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2Base
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SpacecraftBaseRestMapperTest extends AbstractSpacecraftMapperTest {

	private SpacecraftBaseRestMapper spacecraftBaseRestMapper

	void setup() {
		spacecraftBaseRestMapper = Mappers.getMapper(SpacecraftBaseRestMapper)
	}

	void "maps SpacecraftRestBeanParams to SpacecraftRequestDTO"() {
		given:
		SpacecraftRestBeanParams spacecraftRestBeanParams = new SpacecraftRestBeanParams(
				uid: UID,
				name: NAME)

		when:
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftBaseRestMapper.mapBase spacecraftRestBeanParams

		then:
		spacecraftRequestDTO.uid == UID
		spacecraftRequestDTO.name == NAME
	}

	void "maps SpacecraftV2RestBeanParams to SpacecraftRequestDTO"() {
		given:
		SpacecraftV2RestBeanParams spacecraftV2RestBeanParams = new SpacecraftV2RestBeanParams(
				uid: UID,
				name: NAME,
				registry: REGISTRY,
				status: STATUS)

		when:
		SpacecraftRequestDTO spacecraftRequestDTO = spacecraftBaseRestMapper.mapV2Base spacecraftV2RestBeanParams

		then:
		spacecraftRequestDTO.uid == UID
		spacecraftRequestDTO.name == NAME
		spacecraftRequestDTO.registry == REGISTRY
		spacecraftRequestDTO.status == STATUS
	}

	void "maps DB entity to base REST entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftBase spacecraftBase = spacecraftBaseRestMapper.mapBase(Lists.newArrayList(spacecraft))[0]

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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Spacecraft spacecraft = createSpacecraft()

		when:
		SpacecraftV2Base spacecraftBase = spacecraftBaseRestMapper.mapV2Base(Lists.newArrayList(spacecraft))[0]

		then:
		spacecraftBase.uid == UID
		spacecraftBase.name == NAME
		spacecraftBase.registry == REGISTRY
		spacecraftBase.status == STATUS
		spacecraftBase.dateStatus == DATE_STATUS
		spacecraftBase.spacecraftClass != null
		spacecraftBase.owner != null
		spacecraftBase.operator != null
		spacecraftBase.affiliation != null
	}

}
