package com.cezarykluczynski.stapi.server.element.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ElementRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ElementRestBeanParams from PageSortBeanParams"() {
		when:
		ElementRestBeanParams elementRestBeanParams = ElementRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		elementRestBeanParams.pageNumber == PAGE_NUMBER
		elementRestBeanParams.pageSize == PAGE_SIZE
		elementRestBeanParams.sort == SORT
	}

	void "creates null ElementRestBeanParams from null PageSortBeanParams"() {
		when:
		ElementRestBeanParams seriesRestBeanParams = ElementRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
