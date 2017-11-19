package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.util.AbstractAstronomicalObjectTest

class AstronomicalObjectTest extends AbstractAstronomicalObjectTest {

	private AstronomicalObjectApi astronomicalObjectApiMock

	private AstronomicalObject astronomicalObject

	void setup() {
		astronomicalObjectApiMock = Mock()
		astronomicalObject = new AstronomicalObject(astronomicalObjectApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponseOutput = astronomicalObject.get(UID)

		then:
		1 * astronomicalObjectApiMock.astronomicalObjectGet(UID, API_KEY) >> astronomicalObjectFullResponse
		0 * _
		astronomicalObjectFullResponse == astronomicalObjectFullResponseOutput
	}

	void "searches entities"() {
		given:
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectBaseResponseOutput = astronomicalObject.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME,
				ASTRONOMICAL_OBJECT_TYPE, LOCATION_UID)

		then:
		1 * astronomicalObjectApiMock.astronomicalObjectSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, ASTRONOMICAL_OBJECT_TYPE,
				LOCATION_UID) >> astronomicalObjectBaseResponse
		0 * _
		astronomicalObjectBaseResponse == astronomicalObjectBaseResponseOutput
	}

}
