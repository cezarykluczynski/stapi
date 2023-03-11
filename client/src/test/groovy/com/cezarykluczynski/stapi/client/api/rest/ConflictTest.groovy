package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.ConflictSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.ConflictApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractConflictTest

class ConflictTest extends AbstractConflictTest {

	private ConflictApi conflictApiMock

	private Conflict conflict

	void setup() {
		conflictApiMock = Mock()
		conflict = new Conflict(conflictApiMock)
	}

	void "gets single entity"() {
		given:
		ConflictFullResponse conflictFullResponse = Mock()

		when:
		ConflictFullResponse conflictFullResponseOutput = conflict.get(UID)

		then:
		1 * conflictApiMock.v1RestConflictGet(UID) >> conflictFullResponse
		0 * _
		conflictFullResponse == conflictFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		ConflictV2FullResponse conflictV2FullResponse = Mock()

		when:
		ConflictV2FullResponse conflictV2FullResponseOutput = conflict.getV2(UID)

		then:
		1 * conflictApiMock.v2RestConflictGet(UID) >> conflictV2FullResponse
		0 * _
		conflictV2FullResponse == conflictV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		ConflictBaseResponse conflictBaseResponse = Mock()

		when:
		ConflictBaseResponse conflictBaseResponseOutput = conflict.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, YEAR_FROM, YEAR_TO,
				EARTH_CONFLICT, FEDERATION_WAR, KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY)

		then:
		1 * conflictApiMock.v1RestConflictSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, YEAR_FROM, YEAR_TO, EARTH_CONFLICT,
				FEDERATION_WAR, KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY) >> conflictBaseResponse
		0 * _
		conflictBaseResponse == conflictBaseResponseOutput
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
		conflictSearchCriteria.sort.addAll(SORT)

		when:
		ConflictBaseResponse conflictBaseResponseOutput = conflict.search(conflictSearchCriteria)

		then:
		1 * conflictApiMock.v1RestConflictSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, YEAR_FROM, YEAR_TO, EARTH_CONFLICT,
				FEDERATION_WAR, KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY) >> conflictBaseResponse
		0 * _
		conflictBaseResponse == conflictBaseResponseOutput
	}

}
