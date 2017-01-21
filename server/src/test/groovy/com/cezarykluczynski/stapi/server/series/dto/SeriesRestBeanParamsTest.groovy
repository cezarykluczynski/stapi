package com.cezarykluczynski.stapi.server.series.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SeriesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SeriesRestBeanParams from PageSortBeanParams"() {
		when:
		SeriesRestBeanParams seriesRestBeanParams = SeriesRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		seriesRestBeanParams.pageNumber == PAGE_NUMBER
		seriesRestBeanParams.pageSize == PAGE_SIZE
		seriesRestBeanParams.sort == SORT
	}

	void "creates null SeriesRestBeanParams from null PageSortBeanParams"() {
		when:
		SeriesRestBeanParams seriesRestBeanParams = SeriesRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
