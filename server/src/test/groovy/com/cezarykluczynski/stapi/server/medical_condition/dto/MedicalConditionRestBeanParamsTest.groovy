package com.cezarykluczynski.stapi.server.medical_condition.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class MedicalConditionRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates MedicalConditionRestBeanParams from PageSortBeanParams"() {
		when:
		MedicalConditionRestBeanParams medicalConditionRestBeanParams = MedicalConditionRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		medicalConditionRestBeanParams.pageNumber == PAGE_NUMBER
		medicalConditionRestBeanParams.pageSize == PAGE_SIZE
		medicalConditionRestBeanParams.sort == SORT
	}

	void "creates null MedicalConditionRestBeanParams from null PageSortBeanParams"() {
		when:
		MedicalConditionRestBeanParams seriesRestBeanParams = MedicalConditionRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
