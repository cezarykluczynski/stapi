package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.SpacecraftClassV2SearchCriteria
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
		spacecraftClass = new SpacecraftClass(spacecraftClassApiMock)
	}

	void "gets single entity"() {
		given:
		SpacecraftClassFullResponse spacecraftClassFullResponse = Mock()

		when:
		SpacecraftClassFullResponse spacecraftClassFullResponseOutput = spacecraftClass.get(UID)

		then:
		1 * spacecraftClassApiMock.v1RestSpacecraftClassGet(UID, null) >> spacecraftClassFullResponse
		0 * _
		spacecraftClassFullResponse == spacecraftClassFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponse = Mock()

		when:
		SpacecraftClassV2FullResponse spacecraftClassV2FullResponseOutput = spacecraftClass.getV2(UID)

		then:
		1 * spacecraftClassApiMock.v2RestSpacecraftClassGet(UID) >> spacecraftClassV2FullResponse
		0 * _
		spacecraftClassV2FullResponse == spacecraftClassV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = Mock()

		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponseOutput = spacecraftClass.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				WARP_CAPABLE, ALTERNATE_REALITY)

		then:
		1 * spacecraftClassApiMock.v1RestSpacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, NAME, WARP_CAPABLE,
				ALTERNATE_REALITY) >> spacecraftClassBaseResponse
		0 * _
		spacecraftClassBaseResponse == spacecraftClassBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponse = Mock()

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponseOutput = spacecraftClass.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				WARP_CAPABLE, MIRROR, ALTERNATE_REALITY)

		then:
		1 * spacecraftClassApiMock.v2RestSpacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, WARP_CAPABLE, MIRROR,
				ALTERNATE_REALITY) >> spacecraftClassV2BaseResponse
		0 * _
		spacecraftClassV2BaseResponse == spacecraftClassV2BaseResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponse = Mock()
		SpacecraftClassV2SearchCriteria spacecraftClassV2SearchCriteria = new SpacecraftClassV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				warpCapable: WARP_CAPABLE,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY)
		spacecraftClassV2SearchCriteria.sort.addAll(SORT)

		when:
		SpacecraftClassV2BaseResponse spacecraftClassV2BaseResponseOutput = spacecraftClass.searchV2(spacecraftClassV2SearchCriteria)

		then:
		1 * spacecraftClassApiMock.v2RestSpacecraftClassSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, WARP_CAPABLE, MIRROR,
				ALTERNATE_REALITY) >> spacecraftClassV2BaseResponse
		0 * _
		spacecraftClassV2BaseResponse == spacecraftClassV2BaseResponseOutput
	}

}
