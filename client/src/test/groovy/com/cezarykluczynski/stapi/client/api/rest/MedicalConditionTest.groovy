package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.MedicalConditionSearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.MedicalConditionApi
import com.cezarykluczynski.stapi.client.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.util.AbstractMedicalConditionTest

class MedicalConditionTest extends AbstractMedicalConditionTest {

	private MedicalConditionApi medicalConditionApiMock

	private MedicalCondition medicalCondition

	void setup() {
		medicalConditionApiMock = Mock()
		medicalCondition = new MedicalCondition(medicalConditionApiMock)
	}

	void "gets single entity"() {
		given:
		MedicalConditionFullResponse medicalConditionFullResponse = Mock()

		when:
		MedicalConditionFullResponse medicalConditionFullResponseOutput = medicalCondition.get(UID)

		then:
		1 * medicalConditionApiMock.v1RestMedicalConditionGet(UID) >> medicalConditionFullResponse
		0 * _
		medicalConditionFullResponse == medicalConditionFullResponseOutput
	}

	void "searches entities"() {
		given:
		MedicalConditionBaseResponse medicalConditionBaseResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionBaseResponseOutput = medicalCondition.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				PSYCHOLOGICAL_CONDITION)

		then:
		1 * medicalConditionApiMock.v1RestMedicalConditionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, PSYCHOLOGICAL_CONDITION) >>
				medicalConditionBaseResponse
		0 * _
		medicalConditionBaseResponse == medicalConditionBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		MedicalConditionBaseResponse medicalConditionBaseResponse = Mock()
		MedicalConditionSearchCriteria medicalConditionSearchCriteria = new MedicalConditionSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				psychologicalCondition: PSYCHOLOGICAL_CONDITION)
		medicalConditionSearchCriteria.sort.addAll(SORT)

		when:
		MedicalConditionBaseResponse medicalConditionBaseResponseOutput = medicalCondition.search(medicalConditionSearchCriteria)

		then:
		1 * medicalConditionApiMock.v1RestMedicalConditionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, PSYCHOLOGICAL_CONDITION) >>
				medicalConditionBaseResponse
		0 * _
		medicalConditionBaseResponse == medicalConditionBaseResponseOutput
	}

}
