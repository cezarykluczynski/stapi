package com.cezarykluczynski.stapi.server.performer.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class PerformerRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates PerformerRestBeanParams from PageSortBeanParams"() {
		when:
		PerformerRestBeanParams performerRestBeanParams = PerformerRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		performerRestBeanParams.pageNumber == PAGE_NUMBER
		performerRestBeanParams.pageSize == PAGE_SIZE
		performerRestBeanParams.sort == SORT
	}

	void "creates null PerformerRestBeanParams from null PageSortBeanParams"() {
		when:
		PerformerRestBeanParams seriesRestBeanParams = PerformerRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
