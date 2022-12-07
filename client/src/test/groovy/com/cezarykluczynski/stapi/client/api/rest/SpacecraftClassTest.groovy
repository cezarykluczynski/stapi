package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SpacecraftClassApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest

class SpacecraftClassTest extends AbstractSpacecraftClassTest {

	private SpacecraftClassApi spacecraftClassApiMock

	private SpacecraftClass spacecraftClass

	void setup() {
		spacecraftClassApiMock = Mock()
		spacecraftClass = new SpacecraftClass(spacecraftClassApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponseOutput = spacecraftClass.get(UID)

		then:
		1 * spacecraftClassApiMock.v1RestSpacecraftClassGet(UID, API_KEY) >> spacecraftClassFullResponse
		0 * _
		spacecraftClassFullResponse == spacecraftClassFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponse = Mock()

		when:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponseOutput = spacecraftClass.getV2(UID)

		then:
		1 * spacecraftClassApiMock.v2RestSpacecraftClassGet(UID, API_KEY) >> spacecraftClassV2FullResponse
		0 * _
		spacecraftClassV2FullResponse == spacecraftClassV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponseOutput = spacecraftClass.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, WARP_CAPABLE,
				ALTERNATE_REALITY)

		then:
		1 * spacecraftClassApiMock.v1RestSpacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, WARP_CAPABLE, ALTERNATE_REALITY) >>
				spacecraftClassBaseResponse
		0 * _
		spacecraftClassBaseResponse == spacecraftClassBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponse = Mock()

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponseOutput = spacecraftClass.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, WARP_CAPABLE,
				MIRROR, ALTERNATE_REALITY)

		then:
		1 * spacecraftClassApiMock.v2RestSpacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, WARP_CAPABLE, MIRROR,
				ALTERNATE_REALITY) >> spacecraftClassV2BaseResponse
		0 * _
		spacecraftClassV2BaseResponse == spacecraftClassV2BaseResponseOutput
	}

}
