package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

class SpacecraftTest extends AbstractSpacecraftTest {

	private SpacecraftApi spacecraftApiMock

	private Spacecraft spacecraft

	void setup() {
		spacecraftApiMock = Mock()
		spacecraft = new Spacecraft(spacecraftApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SpacecraftFullResponse spacecraftFullResponse = Mock()

		when:
		SpacecraftFullResponse spacecraftFullResponseOutput = spacecraft.get(UID)

		then:
		1 * spacecraftApiMock.spacecraftGet(UID, API_KEY) >> spacecraftFullResponse
		0 * _
		spacecraftFullResponse == spacecraftFullResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftBaseResponse spacecraftBaseResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftBaseResponseOutput = spacecraft.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME)

		then:
		1 * spacecraftApiMock.spacecraftSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME) >> spacecraftBaseResponse
		0 * _
		spacecraftBaseResponse == spacecraftBaseResponseOutput
	}

}
