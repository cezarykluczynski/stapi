package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ConflictFull
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import org.mapstruct.factory.Mappers

class ConflictFullSoapMapperTest extends AbstractConflictMapperTest {

	private ConflictFullSoapMapper conflictFullSoapMapper

	void setup() {
		conflictFullSoapMapper = Mappers.getMapper(ConflictFullSoapMapper)
	}

	void "maps SOAP ConflictFullRequest to ConflictBaseRequestDTO"() {
		given:
		ConflictFullRequest conflictFullRequest = new ConflictFullRequest(uid: UID)

		when:
		ConflictRequestDTO conflictRequestDTO = conflictFullSoapMapper.mapFull conflictFullRequest

		then:
		conflictRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Conflict conflict = createConflict()

		when:
		ConflictFull conflictFull = conflictFullSoapMapper.mapFull(conflict)

		then:
		conflictFull.uid == UID
		conflictFull.name == NAME
		conflictFull.yearFrom == YEAR_FROM
		conflictFull.yearTo == YEAR_TO
		conflictFull.earthConflict == EARTH_CONFLICT
		conflictFull.federationWar == FEDERATION_WAR
		conflictFull.klingonWar == KLINGON_WAR
		conflictFull.dominionWarBattle == DOMINION_WAR_BATTLE
		conflictFull.alternateReality == ALTERNATE_REALITY
		conflictFull.locations.size() == conflict.locations.size()
		conflictFull.firstSideBelligerents.size() == conflict.firstSideBelligerents.size()
		conflictFull.firstSideCommanders.size() == conflict.firstSideCommanders.size()
		conflictFull.secondSideBelligerents.size() == conflict.secondSideBelligerents.size()
		conflictFull.secondSideCommanders.size() == conflict.secondSideCommanders.size()
	}

}
