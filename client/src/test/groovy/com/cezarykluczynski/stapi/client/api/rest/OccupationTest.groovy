package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.OccupationApi
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse
import com.cezarykluczynski.stapi.util.AbstractOccupationTest

class OccupationTest extends AbstractOccupationTest {

	private OccupationApi occupationApiMock

	private Occupation occupation

	void setup() {
		occupationApiMock = Mock()
		occupation = new Occupation(occupationApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		OccupationFullResponse occupationFullResponse = Mock()

		when:
		OccupationFullResponse occupationFullResponseOutput = occupation.get(UID)

		then:
		1 * occupationApiMock.occupationGet(UID, API_KEY) >> occupationFullResponse
		0 * _
		occupationFullResponse == occupationFullResponseOutput
	}

	void "searches entities"() {
		given:
		OccupationBaseResponse occupationBaseResponse = Mock()

		when:
		OccupationBaseResponse occupationBaseResponseOutput = occupation.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, LEGAL_OCCUPATION,
				MEDICAL_OCCUPATION, SCIENTIFIC_OCCUPATION)

		then:
		1 * occupationApiMock.occupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION) >> occupationBaseResponse
		0 * _
		occupationBaseResponse == occupationBaseResponseOutput
	}

}
