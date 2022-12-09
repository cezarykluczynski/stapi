package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Conflict conflict = createConflict()

		when:
		ConflictV2Full conflictV2Full = conflictFullRestMapper.mapV2Full(conflict)

		then:
		conflictV2Full.uid == UID
		conflictV2Full.name == NAME
		conflictV2Full.yearFrom == YEAR_FROM
		conflictV2Full.yearTo == YEAR_TO
		conflictV2Full.earthConflict == EARTH_CONFLICT
		conflictV2Full.federationWar == FEDERATION_WAR
		conflictV2Full.klingonWar == KLINGON_WAR
		conflictV2Full.dominionWarBattle == DOMINION_WAR_BATTLE
		conflictV2Full.alternateReality == ALTERNATE_REALITY
		conflictV2Full.locations.size() == conflict.locations.size()
		conflictV2Full.firstSideBelligerents.size() == conflict.firstSideBelligerents.size()
		conflictV2Full.firstSideLocations.size() == conflict.firstSideLocations.size()
		conflictV2Full.firstSideCommanders.size() == conflict.firstSideCommanders.size()
		conflictV2Full.secondSideBelligerents.size() == conflict.secondSideBelligerents.size()
		conflictV2Full.secondSideLocations.size() == conflict.secondSideLocations.size()
		conflictV2Full.secondSideCommanders.size() == conflict.secondSideCommanders.size()
	}

}
