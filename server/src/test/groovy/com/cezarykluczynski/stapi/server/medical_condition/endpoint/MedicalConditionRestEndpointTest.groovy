package com.cezarykluczynski.stapi.server.medical_condition.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.medical_condition.dto.MedicalConditionRestBeanParams
import com.cezarykluczynski.stapi.server.medical_condition.reader.MedicalConditionRestReader

class MedicalConditionRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private MedicalConditionRestReader medicalConditionRestReaderMock

	private MedicalConditionRestEndpoint medicalConditionRestEndpoint

	void setup() {
		medicalConditionRestReaderMock = Mock()
		medicalConditionRestEndpoint = new MedicalConditionRestEndpoint(medicalConditionRestReaderMock)
	}

	void "passes get call to MedicalConditionRestReader"() {
		given:
		MedicalConditionFullResponse medicalConditionFullResponse = Mock()

		when:
		MedicalConditionFullResponse medicalConditionFullResponseOutput = medicalConditionRestEndpoint.getMedicalCondition(UID)

		then:
		1 * medicalConditionRestReaderMock.readFull(UID) >> medicalConditionFullResponse
		medicalConditionFullResponseOutput == medicalConditionFullResponse
	}

	void "passes search get call to MedicalConditionRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		MedicalConditionBaseResponse medicalConditionResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionResponseOutput = medicalConditionRestEndpoint.searchMedicalConditions(pageAwareBeanParams)

		then:
		1 * medicalConditionRestReaderMock.readBase(_ as MedicalConditionRestBeanParams) >> {
				MedicalConditionRestBeanParams medicalConditionRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			medicalConditionResponse
		}
		medicalConditionResponseOutput == medicalConditionResponse
	}

	void "passes search post call to MedicalConditionRestReader"() {
		given:
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = new MedicalConditionRestBeanParams(name: NAME)
		MedicalConditionBaseResponse medicalConditionResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionResponseOutput = medicalConditionRestEndpoint
				.searchMedicalConditions(medicalConditionRestBeanParams)

		then:
		1 * medicalConditionRestReaderMock.readBase(medicalConditionRestBeanParams as MedicalConditionRestBeanParams) >> {
				MedicalConditionRestBeanParams params ->
			assert params.name == NAME
			medicalConditionResponse
		}
		medicalConditionResponseOutput == medicalConditionResponse
	}

}
