package com.cezarykluczynski.stapi.server.performer.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class PerformerV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates PerformerV2RestBeanParams from PageSortBeanParams"() {
		when:
		PerformerV2RestBeanParams performerV2RestBeanParams = PerformerV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		performerV2RestBeanParams.pageNumber == PAGE_NUMBER
		performerV2RestBeanParams.pageSize == PAGE_SIZE
		performerV2RestBeanParams.sort == SORT
	}

	void "creates null PerformerV2RestBeanParams from null PageSortBeanParams"() {
		when:
		PerformerV2RestBeanParams performerV2RestBeanParams = PerformerV2RestBeanParams.fromPageSortBeanParams null

		then:
		performerV2RestBeanParams == null
	}

}
