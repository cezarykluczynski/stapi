package com.cezarykluczynski.stapi.server.staff.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class StaffRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates StaffRestBeanParams from PageSortBeanParams"() {
		when:
		StaffRestBeanParams staffRestBeanParams = StaffRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		staffRestBeanParams.pageNumber == PAGE_NUMBER
		staffRestBeanParams.pageSize == PAGE_SIZE
		staffRestBeanParams.sort == SORT
	}

	void "creates null StaffRestBeanParams from null PageSortBeanParams"() {
		when:
		StaffRestBeanParams seriesRestBeanParams = StaffRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
