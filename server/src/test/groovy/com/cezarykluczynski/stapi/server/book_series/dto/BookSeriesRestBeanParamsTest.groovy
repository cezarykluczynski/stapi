package com.cezarykluczynski.stapi.server.book_series.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class BookSeriesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates BookSeriesRestBeanParams from PageSortBeanParams"() {
		when:
		BookSeriesRestBeanParams bookSeriesRestBeanParams = BookSeriesRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		bookSeriesRestBeanParams.pageNumber == PAGE_NUMBER
		bookSeriesRestBeanParams.pageSize == PAGE_SIZE
		bookSeriesRestBeanParams.sort == SORT
	}

	void "creates null BookSeriesRestBeanParams from null PageSortBeanParams"() {
		when:
		BookSeriesRestBeanParams seriesRestBeanParams = BookSeriesRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
