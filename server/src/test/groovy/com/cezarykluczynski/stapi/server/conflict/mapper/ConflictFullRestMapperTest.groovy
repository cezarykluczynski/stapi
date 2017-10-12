package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFull
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import org.mapstruct.factory.Mappers

class ConflictFullRestMapperTest extends AbstractConflictMapperTest {

	private ConflictFullRestMapper conflictFullRestMapper

	void setup() {
		conflictFullRestMapper = Mappers.getMapper(ConflictFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Conflict conflict = createConflict()

		when:
		ConflictFull conflictFull = conflictFullRestMapper.mapFull(conflict)

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
