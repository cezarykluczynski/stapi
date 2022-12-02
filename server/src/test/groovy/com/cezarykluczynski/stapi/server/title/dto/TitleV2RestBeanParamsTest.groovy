package com.cezarykluczynski.stapi.server.title.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TitleV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TitleV2RestBeanParams from PageSortBeanParams"() {
		when:
		TitleV2RestBeanParams titleV2RestBeanParams = TitleV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		titleV2RestBeanParams.pageNumber == PAGE_NUMBER
		titleV2RestBeanParams.pageSize == PAGE_SIZE
		titleV2RestBeanParams.sort == SORT
	}

	void "creates null TitleV2RestBeanParams from null PageSortBeanParams"() {
		when:
		TitleV2RestBeanParams titleV2RestBeanParams = TitleV2RestBeanParams.fromPageSortBeanParams null

		then:
		titleV2RestBeanParams == null
	}

}
