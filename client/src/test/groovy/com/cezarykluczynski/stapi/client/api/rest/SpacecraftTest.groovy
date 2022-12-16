package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

class SpacecraftTest extends AbstractSpacecraftTest {

	private SpacecraftApi spacecraftApiMock

	private Spacecraft spacecraft

	void setup() {
		spacecraftApiMock = Mock()
		spacecraft = new Spacecraft(spacecraftApiMock)
	}

	void "gets single entity"() {
		given:
		SpacecraftFullResponse spacecraftFullResponse = Mock()

		when:
		SpacecraftFullResponse spacecraftFullResponseOutput = spacecraft.get(UID)

		then:
		1 * spacecraftApiMock.v1RestSpacecraftGet(UID, null) >> spacecraftFullResponse
		0 * _
		spacecraftFullResponse == spacecraftFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		SpacecraftV2FullResponse spacecraftV2FullResponse = Mock()

		when:
		SpacecraftV2FullResponse spacecraftV2FullResponseOutput = spacecraft.getV2(UID)

		then:
		1 * spacecraftApiMock.v2RestSpacecraftGet(UID) >> spacecraftV2FullResponse
		0 * _
		spacecraftV2FullResponse == spacecraftV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftBaseResponse spacecraftBaseResponse = Mock()

		when:
		SpacecraftBaseResponse spacecraftBaseResponseOutput = spacecraft.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME)

		then:
		1 * spacecraftApiMock.v1RestSpacecraftSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME) >> spacecraftBaseResponse
		0 * _
		spacecraftBaseResponse == spacecraftBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		SpacecraftV2BaseResponse spacecraftV2BaseResponse = Mock()

		when:
		SpacecraftV2BaseResponse spacecraftV2BaseResponseOutput = spacecraft.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, REGISTRY, STATUS)

		then:
		1 * spacecraftApiMock.v2RestSpacecraftSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, REGISTRY, STATUS) >> spacecraftV2BaseResponse
		0 * _
		spacecraftV2BaseResponse == spacecraftV2BaseResponseOutput
	}

}
