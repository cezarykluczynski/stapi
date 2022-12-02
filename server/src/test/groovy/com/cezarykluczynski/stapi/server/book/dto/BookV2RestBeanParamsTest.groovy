package com.cezarykluczynski.stapi.server.book.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class BookV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates BookV2RestBeanParams from PageSortBeanParams"() {
		when:
		BookV2RestBeanParams bookV2RestBeanParams = BookV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		bookV2RestBeanParams.pageNumber == PAGE_NUMBER
		bookV2RestBeanParams.pageSize == PAGE_SIZE
		bookV2RestBeanParams.sort == SORT
	}

	void "creates null BookV2RestBeanParams from null PageSortBeanParams"() {
		when:
		BookV2RestBeanParams bookV2RestBeanParams = BookV2RestBeanParams.fromPageSortBeanParams null

		then:
		bookV2RestBeanParams == null
	}

}
