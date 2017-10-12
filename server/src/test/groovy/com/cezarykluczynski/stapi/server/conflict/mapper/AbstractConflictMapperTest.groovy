package com.cezarykluczynski.stapi.server.conflict.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.util.AbstractConflictTest

abstract class AbstractConflictMapperTest extends AbstractConflictTest {

	protected Conflict createConflict() {
		new Conflict(
				uid: UID,
				name: NAME,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				earthConflict: EARTH_CONFLICT,
				federationWar: FEDERATION_WAR,
				klingonWar: KLINGON_WAR,
				dominionWarBattle: DOMINION_WAR_BATTLE,
				alternateReality: ALTERNATE_REALITY,
				locations: createSetOfRandomNumberOfMocks(Location),
				firstSideBelligerents: createSetOfRandomNumberOfMocks(Organization),
				firstSideCommanders: createSetOfRandomNumberOfMocks(Character),
				secondSideBelligerents: createSetOfRandomNumberOfMocks(Organization),
				secondSideCommanders: createSetOfRandomNumberOfMocks(Character))
	}

}
