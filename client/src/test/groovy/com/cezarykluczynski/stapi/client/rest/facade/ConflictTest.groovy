package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.ConflictApi
import com.cezarykluczynski.stapi.client.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ConflictSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractConflictTest

class ConflictTest extends AbstractConflictTest {

	private ConflictApi conflictApiMock

	private Conflict conflict

	void setup() {
		conflictApiMock = Mock()
		conflict = new Conflict(conflictApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		ConflictV2FullResponse conflictV2FullResponse = Mock()

		when:
		ConflictV2FullResponse conflictV2FullResponseOutput = conflict.getV2(UID)

		then:
		1 * conflictApiMock.v2GetConflict(UID) >> conflictV2FullResponse
		0 * _
		conflictV2FullResponse == conflictV2FullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		ConflictBaseResponse conflictBaseResponse = Mock()
		ConflictSearchCriteria conflictSearchCriteria = new ConflictSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				earthConflict: EARTH_CONFLICT,
				federationWar: FEDERATION_WAR,
				klingonWar: KLINGON_WAR,
				dominionWarBattle: DOMINION_WAR_BATTLE,
				alternateReality: ALTERNATE_REALITY)
		conflictSearchCriteria.sort = SORT

		when:
		ConflictBaseResponse conflictBaseResponseOutput = conflict.search(conflictSearchCriteria)

		then:
		1 * conflictApiMock.v1SearchConflicts(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, YEAR_FROM, YEAR_TO, EARTH_CONFLICT,
				FEDERATION_WAR, KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY) >> conflictBaseResponse
		0 * _
		conflictBaseResponse == conflictBaseResponseOutput
	}

}
