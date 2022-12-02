package com.cezarykluczynski.stapi.server.staff.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class StaffV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates StaffV2RestBeanParams from PageSortBeanParams"() {
		when:
		StaffV2RestBeanParams staffV2RestBeanParams = StaffV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		staffV2RestBeanParams.pageNumber == PAGE_NUMBER
		staffV2RestBeanParams.pageSize == PAGE_SIZE
		staffV2RestBeanParams.sort == SORT
	}

	void "creates null StaffV2RestBeanParams from null PageSortBeanParams"() {
		when:
		StaffV2RestBeanParams staffV2RestBeanParams = StaffV2RestBeanParams.fromPageSortBeanParams null

		then:
		staffV2RestBeanParams == null
	}

}
