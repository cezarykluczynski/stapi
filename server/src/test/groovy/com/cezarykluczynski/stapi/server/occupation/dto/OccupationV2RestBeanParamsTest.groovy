package com.cezarykluczynski.stapi.server.occupation.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class OccupationV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates OccupationV2RestBeanParams from PageSortBeanParams"() {
		when:
		OccupationV2RestBeanParams occupationV2RestBeanParams = OccupationV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		occupationV2RestBeanParams.pageNumber == PAGE_NUMBER
		occupationV2RestBeanParams.pageSize == PAGE_SIZE
		occupationV2RestBeanParams.sort == SORT
	}

	void "creates null OccupationV2RestBeanParams from null PageSortBeanParams"() {
		when:
		OccupationV2RestBeanParams occupationV2RestBeanParams = OccupationV2RestBeanParams.fromPageSortBeanParams null

		then:
		occupationV2RestBeanParams == null
	}

}
