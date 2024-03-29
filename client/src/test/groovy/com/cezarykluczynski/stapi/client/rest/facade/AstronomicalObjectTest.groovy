package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.AstronomicalObjectApi
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2SearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest

class AstronomicalObjectTest extends AbstractAstronomicalObjectTest {

	private AstronomicalObjectApi astronomicalObjectApiMock

	private AstronomicalObject astronomicalObject

	void setup() {
		astronomicalObjectApiMock = Mock()
		astronomicalObject = new AstronomicalObject(astronomicalObjectApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponse = Mock()

		when:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponseOutput = astronomicalObject.getV2(UID)

		then:
		1 * astronomicalObjectApiMock.v2GetAstronomicalObject(UID) >> astronomicalObjectV2FullResponse
		0 * _
		astronomicalObjectV2FullResponse == astronomicalObjectV2FullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponse = Mock()
		AstronomicalObjectV2SearchCriteria astronomicalObjectV2SearchCriteria = new AstronomicalObjectV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				astronomicalObjectType: AstronomicalObjectV2Type.valueOf(ASTRONOMICAL_OBJECT_TYPE),
				locationUid: LOCATION_UID)
		astronomicalObjectV2SearchCriteria.sort = SORT

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2BaseResponseOutput = astronomicalObject.searchV2(astronomicalObjectV2SearchCriteria)

		then:
		1 * astronomicalObjectApiMock.v2SearchAstronomicalObjects(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ASTRONOMICAL_OBJECT_TYPE,
				LOCATION_UID) >> astronomicalObjectV2BaseResponse
		0 * _
		astronomicalObjectV2BaseResponse == astronomicalObjectV2BaseResponseOutput
	}

}
