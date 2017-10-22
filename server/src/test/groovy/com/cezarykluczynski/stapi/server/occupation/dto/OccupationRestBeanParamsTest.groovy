package com.cezarykluczynski.stapi.server.occupation.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class OccupationRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates OccupationRestBeanParams from PageSortBeanParams"() {
		when:
		OccupationRestBeanParams occupationRestBeanParams = OccupationRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		occupationRestBeanParams.pageNumber == PAGE_NUMBER
		occupationRestBeanParams.pageSize == PAGE_SIZE
		occupationRestBeanParams.sort == SORT
	}

	void "creates null OccupationRestBeanParams from null PageSortBeanParams"() {
		when:
		OccupationRestBeanParams seriesRestBeanParams = OccupationRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
