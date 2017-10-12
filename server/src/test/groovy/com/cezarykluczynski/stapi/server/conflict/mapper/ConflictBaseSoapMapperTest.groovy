package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBase as ConflictBase
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ConflictBaseSoapMapperTest extends AbstractConflictMapperTest {

	private ConflictBaseSoapMapper conflictBaseSoapMapper

	void setup() {
		conflictBaseSoapMapper = Mappers.getMapper(ConflictBaseSoapMapper)
	}

	void "maps SOAP ConflictRequest to ConflictRequestDTO"() {
		given:
		ConflictBaseRequest conflictBaseRequest = new ConflictBaseRequest(name: NAME)

		when:
		ConflictRequestDTO conflictRequestDTO = conflictBaseSoapMapper.mapBase conflictBaseRequest

		then:
		conflictRequestDTO.name == NAME
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Conflict conflict = createConflict()

		when:
		ConflictBase conflictBase = conflictBaseSoapMapper.mapBase(Lists.newArrayList(conflict))[0]

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
