package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SpacecraftClassApi
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV3FullResponse
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest

class SpacecraftClassTest extends AbstractSpacecraftClassTest {

	private SpacecraftClassApi spacecraftClassApiMock

	private SpacecraftClass spacecraftClass

	void setup() {
		spacecraftClassApiMock = Mock()
		spacecraftClass = new SpacecraftClass(spacecraftClassApiMock)
	}

	void "gets single entity (V3)"() {
		given:
		SpacecraftClassV3FullResponse spacecraftClassV3FullResponse = Mock()

		when:
		SpacecraftClassV3FullResponse spacecraftClassV3FullResponseOutput = spacecraftClass.getV3(UID)

		then:
		1 * spacecraftClassApiMock.v3GetSpacecraftClass(UID) >> spacecraftClassV3FullResponse
		0 * _
		spacecraftClassV3FullResponse == spacecraftClassV3FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponse = Mock()
		SpacecraftClassV2SearchCriteria spacecraftClassV2SearchCriteria = new SpacecraftClassV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				warpCapableSpecies: WARP_CAPABLE_SPECIES,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		spacecraftClassV2SearchCriteria.sort = SORT

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponseOutput = spacecraftClass.searchV2(spacecraftClassV2SearchCriteria)

		then:
		1 * spacecraftClassApiMock.v2SearchSpacecraftClasses(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, WARP_CAPABLE_SPECIES, MIRROR,
				ALTERNATE_REALITY) >> spacecraftClassV2BaseResponse
		0 * _
		spacecraftClassV2BaseResponse == spacecraftClassV2BaseResponseOutput
	}

}
