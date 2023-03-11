package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.OccupationV2SearchCriteria
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
		1 * occupationApiMock.v1RestOccupationGet(UID) >> occupationFullResponse
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
		OccupationBaseResponse occupationBaseResponseOutput = occupation.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, LEGAL_OCCUPATION,
				MEDICAL_OCCUPATION, SCIENTIFIC_OCCUPATION)

		then:
		1 * occupationApiMock.v1RestOccupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION) >> occupationBaseResponse
		0 * _
		occupationBaseResponse == occupationBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		OccupationV2BaseResponse occupationV2BaseResponse = Mock()

		when:
		OccupationV2BaseResponse occupationV2BaseResponseOutput = occupation.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ARTS_OCCUPATION,
				COMMUNICATION_OCCUPATION, ECONOMIC_OCCUPATION, EDUCATION_OCCUPATION, ENTERTAINMENT_OCCUPATION, ILLEGAL_OCCUPATION, LEGAL_OCCUPATION,
				MEDICAL_OCCUPATION, SCIENTIFIC_OCCUPATION, SPORTS_OCCUPATION, VICTUAL_OCCUPATION)

		then:
		1 * occupationApiMock.v2RestOccupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ARTS_OCCUPATION, COMMUNICATION_OCCUPATION,
				ECONOMIC_OCCUPATION, EDUCATION_OCCUPATION, ENTERTAINMENT_OCCUPATION, ILLEGAL_OCCUPATION, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION, SPORTS_OCCUPATION, VICTUAL_OCCUPATION) >> occupationV2BaseResponse
		0 * _
		occupationV2BaseResponse == occupationV2BaseResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		OccupationV2BaseResponse occupationV2BaseResponse = Mock()
		OccupationV2SearchCriteria occupationV2SearchCriteria = new OccupationV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				artsOccupation: ARTS_OCCUPATION,
				communicationOccupation: COMMUNICATION_OCCUPATION,
				economicOccupation: ECONOMIC_OCCUPATION,
				educationOccupation: EDUCATION_OCCUPATION,
				entertainmentOccupation: ENTERTAINMENT_OCCUPATION,
				illegalOccupation: ILLEGAL_OCCUPATION,
				legalOccupation: LEGAL_OCCUPATION,
				medicalOccupation: MEDICAL_OCCUPATION,
				scientificOccupation: SCIENTIFIC_OCCUPATION,
				sportsOccupation: SPORTS_OCCUPATION,
				victualOccupation: VICTUAL_OCCUPATION)
		occupationV2SearchCriteria.sort.addAll(SORT)

		when:
		OccupationV2BaseResponse occupationV2BaseResponseOutput = occupation.searchV2(occupationV2SearchCriteria)

		then:
		1 * occupationApiMock.v2RestOccupationSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, ARTS_OCCUPATION, COMMUNICATION_OCCUPATION,
				ECONOMIC_OCCUPATION, EDUCATION_OCCUPATION, ENTERTAINMENT_OCCUPATION, ILLEGAL_OCCUPATION, LEGAL_OCCUPATION, MEDICAL_OCCUPATION,
				SCIENTIFIC_OCCUPATION, SPORTS_OCCUPATION, VICTUAL_OCCUPATION) >> occupationV2BaseResponse
		0 * _
		occupationV2BaseResponse == occupationV2BaseResponseOutput
	}

}
