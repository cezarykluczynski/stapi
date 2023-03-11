package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.AstronomicalObjectV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.AstronomicalObjectApi
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectType
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest

class AstronomicalObjectTest extends AbstractAstronomicalObjectTest {

	private AstronomicalObjectApi astronomicalObjectApiMock

	private AstronomicalObject astronomicalObject

	void setup() {
		astronomicalObjectApiMock = Mock()
		astronomicalObject = new AstronomicalObject(astronomicalObjectApiMock)
	}

	void "gets single entity"() {
		given:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponseOutput = astronomicalObject.get(UID)

		then:
		1 * astronomicalObjectApiMock.v1RestAstronomicalObjectGet(UID) >> astronomicalObjectFullResponse
		0 * _
		astronomicalObjectFullResponse == astronomicalObjectFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponse = Mock()

		when:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponseOutput = astronomicalObject.getV2(UID)

		then:
		1 * astronomicalObjectApiMock.v2RestAstronomicalObjectGet(UID) >> astronomicalObjectV2FullResponse
		0 * _
		astronomicalObjectV2FullResponse == astronomicalObjectV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponseOutput = astronomicalObject.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				ASTRONOMICAL_OBJECT_TYPE, LOCATION_UID)

		then:
		1 * astronomicalObjectApiMock.v1RestAstronomicalObjectSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ASTRONOMICAL_OBJECT_TYPE,
				LOCATION_UID) >> astronomicalObjectBaseResponse
		0 * _
		astronomicalObjectBaseResponse == astronomicalObjectBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponse = Mock()

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponseOutput = astronomicalObject.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				ASTRONOMICAL_OBJECT_TYPE, LOCATION_UID)

		then:
		1 * astronomicalObjectApiMock.v2RestAstronomicalObjectSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				ASTRONOMICAL_OBJECT_TYPE, LOCATION_UID) >> astronomicalObjectV2BaseResponse
		0 * _
		astronomicalObjectV2BaseResponse == astronomicalObjectV2BaseResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponse = Mock()
		AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria = new AstronomicalObjectV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				astronomicalObjectType: AstronomicalObjectType.valueOf(ASTRONOMICAL_OBJECT_TYPE),
				locationUid: LOCATION_UID)
		astronomicalObjectV2SearchCriteria.sort.addAll(SORT)

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponseOutput = astronomicalObject.searchV2(astronomicalObjectV2SearchCriteria)

		then:
		1 * astronomicalObjectApiMock.v2RestAstronomicalObjectSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ASTRONOMICAL_OBJECT_TYPE,
				LOCATION_UID) >> astronomicalObjectV2BaseResponse
		0 * _
		astronomicalObjectV2BaseResponse == astronomicalObjectV2BaseResponseOutput
	}

}
