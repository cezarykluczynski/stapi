package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ConflictApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse
import com.cezarykluczynski.stapi.util.AbstractConflictTest

class ConflictTest extends AbstractConflictTest {

	private ConflictApi conflictApiMock

	private Conflict conflict

	void setup() {
		conflictApiMock = Mock()
		conflict = new Conflict(conflictApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ConflictFullResponse conflictFullResponse = Mock()

		when:
		ConflictFullResponse conflictFullResponseOutput = conflict.get(UID)

		then:
		1 * conflictApiMock.conflictGet(UID, API_KEY) >> conflictFullResponse
		0 * _
		conflictFullResponse == conflictFullResponseOutput
	}

	void "searches entities"() {
		given:
		ConflictBaseResponse conflictBaseResponse = Mock()

		when:
		ConflictBaseResponse conflictBaseResponseOutput = conflict.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, YEAR_FROM, YEAR_TO, EARTH_CONFLICT,
				FEDERATION_WAR, KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY)

		then:
		1 * conflictApiMock.conflictSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, YEAR_FROM, YEAR_TO, EARTH_CONFLICT, FEDERATION_WAR,
				KLINGON_WAR, DOMINION_WAR_BATTLE, ALTERNATE_REALITY) >> conflictBaseResponse
		0 * _
		conflictBaseResponse == conflictBaseResponseOutput
	}

}
