package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SpacecraftApi
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

class SpacecraftTest extends AbstractSpacecraftTest {

	private SpacecraftApi spacecraftApiMock

	private Spacecraft spacecraft

	void setup() {
		spacecraftApiMock = Mock()
		spacecraft = new Spacecraft(spacecraftApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		SpacecraftV2FullResponse spacecraftV2FullResponse = Mock()

		when:
		SpacecraftV2FullResponse spacecraftV2FullResponseOutput = spacecraft.getV2(UID)

		then:
		1 * spacecraftApiMock.v2GetSpacecraft(UID) >> spacecraftV2FullResponse
		0 * _
		spacecraftV2FullResponse == spacecraftV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		SpacecraftV2BaseResponse spacecraftV2BaseResponse = Mock()
		SpacecraftV2SearchCriteria spacecraftV2SearchCriteria = new SpacecraftV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				registry: REGISTRY,
				status: STATUS)
		spacecraftV2SearchCriteria.sort = SORT

		when:
		SpacecraftV2BaseResponse spacecraftV2BaseResponseOutput = spacecraft.searchV2(spacecraftV2SearchCriteria)

		then:
		1 * spacecraftApiMock.v2SearchSpacecrafts(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, REGISTRY, STATUS) >> spacecraftV2BaseResponse
		0 * _
		spacecraftV2BaseResponse == spacecraftV2BaseResponseOutput
	}

}
