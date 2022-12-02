package com.cezarykluczynski.stapi.server.element.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ElementV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ElementV2RestBeanParams from PageSortBeanParams"() {
		when:
		ElementV2RestBeanParams elementV2RestBeanParams = ElementV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		elementV2RestBeanParams.pageNumber == PAGE_NUMBER
		elementV2RestBeanParams.pageSize == PAGE_SIZE
		elementV2RestBeanParams.sort == SORT
	}

	void "creates null ElementV2RestBeanParams from null PageSortBeanParams"() {
		when:
		ElementV2RestBeanParams elementV2RestBeanParams = ElementV2RestBeanParams.fromPageSortBeanParams null

		then:
		elementV2RestBeanParams == null
	}

}
