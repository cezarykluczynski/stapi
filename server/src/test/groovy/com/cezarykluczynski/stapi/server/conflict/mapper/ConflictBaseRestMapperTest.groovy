package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBase
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ConflictBaseRestMapperTest extends AbstractConflictMapperTest {

	private ConflictBaseRestMapper conflictBaseRestMapper

	void setup() {
		conflictBaseRestMapper = Mappers.getMapper(ConflictBaseRestMapper)
	}

	void "maps ConflictRestBeanParams to ConflictRequestDTO"() {
		given:
		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams(
				uid: UID,
				name: NAME)

		when:
		ConflictRequestDTO conflictRequestDTO = conflictBaseRestMapper.mapBase conflictRestBeanParams

		then:
		conflictRequestDTO.uid == UID
		conflictRequestDTO.name == NAME
	}

	void "maps DB entity to base REST entity"() {
		given:
		Conflict conflict = createConflict()

		when:
		ConflictBase conflictBase = conflictBaseRestMapper.mapBase(Lists.newArrayList(conflict))[0]

		then:
		conflictBase.uid == UID
		conflictBase.name == NAME
		conflictBase.yearFrom == YEAR_FROM
		conflictBase.yearTo == YEAR_TO
		conflictBase.earthConflict == EARTH_CONFLICT
		conflictBase.federationWar == FEDERATION_WAR
		conflictBase.klingonWar == KLINGON_WAR
		conflictBase.dominionWarBattle == DOMINION_WAR_BATTLE
		conflictBase.alternateReality == ALTERNATE_REALITY
	}

}
