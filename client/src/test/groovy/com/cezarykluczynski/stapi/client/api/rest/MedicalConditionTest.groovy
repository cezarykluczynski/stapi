package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.MedicalConditionApi
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.util.AbstractMedicalConditionTest

class MedicalConditionTest extends AbstractMedicalConditionTest {

	private MedicalConditionApi medicalConditionApiMock

	private MedicalCondition medicalCondition

	void setup() {
		medicalConditionApiMock = Mock()
		medicalCondition = new MedicalCondition(medicalConditionApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		MedicalConditionFullResponse medicalConditionFullResponse = Mock()

		when:
		MedicalConditionFullResponse medicalConditionFullResponseOutput = medicalCondition.get(UID)

		then:
		1 * medicalConditionApiMock.medicalConditionGet(UID, API_KEY) >> medicalConditionFullResponse
		0 * _
		medicalConditionFullResponse == medicalConditionFullResponseOutput
	}

	void "searches entities"() {
		given:
		MedicalConditionBaseResponse medicalConditionBaseResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionBaseResponseOutput = medicalCondition.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME,
				PSYCHOLOGICAL_CONDITION)

		then:
		1 * medicalConditionApiMock.medicalConditionSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, PSYCHOLOGICAL_CONDITION) >>
				medicalConditionBaseResponse
		0 * _
		medicalConditionBaseResponse == medicalConditionBaseResponseOutput
	}

}
