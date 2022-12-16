package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.OccupationApi
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2FullResponse
import com.cezarykluczynski.stapi.util.AbstractOccupationTest

class OccupationTest extends AbstractOccupationTest {

	private OccupationApi occupationApiMock

	private Occupation occupation

	void setup() {
		occupationApiMock = Mock()
		occupation = new Occupation(occupationApiMock)
	}

	void "gets single entity"() {
		given:
		OccupationFullResponse occupationFullResponse = Mock()

		when:
		OccupationFullResponse occupationFullResponseOutput = occupation.get(UID)

		then:
		1 * occupationApiMock.v1RestOccupationGet(UID, null) >> occupationFullResponse
		0 * _
		occupationFullResponse == occupationFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		OccupationV2FullResponse occupationV2FullResponse = Mock()

		when:
		OccupationV2FullResponse occupationV2FullResponseOutput = occupation.getV2(UID)

		then:
		1 * occupationApiMock.v2RestOccupationGet(UID) >> occupationV2FullResponse
		0 * _
		occupationV2FullResponse == occupationV2FullResponseOutput
	}

	void "searches entities"() {
		given:
		OccupationBaseResponse occupationBaseResponse = Mock()

		when:
		OccupationBaseResponse occupationBaseResponseOutput = occupation.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, LEGAL_OCCUPATION,
				MEDICAL_OCCUPATION, SCIENTIFIC_OCCUPATION)

		then:
		1 * occupationApiMock.v1RestOccupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, NAME, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION) >> occupationBaseResponse
		0 * _
		occupationBaseResponse == occupationBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		OccupationV2BaseResponse occupationV2BaseResponse = Mock()

		when:
		OccupationV2BaseResponse occupationV2BaseResponseOutput = occupation.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, ARTS_OCCUPATION,
				COMMUNICATION_OCCUPATION, ECONOMIC_OCCUPATION, EDUCATION_OCCUPATION, ENTERTAINMENT_OCCUPATION, ILLEGAL_OCCUPATION, LEGAL_OCCUPATION,
				MEDICAL_OCCUPATION, SCIENTIFIC_OCCUPATION, SPORTS_OCCUPATION, VICTUAL_OCCUPATION)

		then:
		1 * occupationApiMock.v2RestOccupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, ARTS_OCCUPATION, COMMUNICATION_OCCUPATION,
				ECONOMIC_OCCUPATION, EDUCATION_OCCUPATION, ENTERTAINMENT_OCCUPATION, ILLEGAL_OCCUPATION, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION, SPORTS_OCCUPATION, VICTUAL_OCCUPATION) >> occupationV2BaseResponse
		0 * _
		occupationV2BaseResponse == occupationV2BaseResponseOutput
	}

}
