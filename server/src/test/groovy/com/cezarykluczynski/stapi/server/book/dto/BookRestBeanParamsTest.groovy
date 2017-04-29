package com.cezarykluczynski.stapi.server.book.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class BookRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates BookRestBeanParams from PageSortBeanParams"() {
		when:
		BookRestBeanParams bookRestBeanParams = BookRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		bookRestBeanParams.pageNumber == PAGE_NUMBER
		bookRestBeanParams.pageSize == PAGE_SIZE
		bookRestBeanParams.sort == SORT
	}

	void "creates null BookRestBeanParams from null PageSortBeanParams"() {
		when:
		BookRestBeanParams seriesRestBeanParams = BookRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
