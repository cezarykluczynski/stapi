package com.cezarykluczynski.stapi.server.series.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams

class SeriesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	def "creates SeriesRestBeanParams from PageAwareBeanParams"() {
		when:
		SeriesRestBeanParams seriesRestBeanParams = SeriesRestBeanParams.fromPageAwareBeanParams(new PageAwareBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE
		))

		then:
		seriesRestBeanParams.pageNumber == PAGE_NUMBER
		seriesRestBeanParams.pageSize == PAGE_SIZE
	}

	def "creates null SeriesRestBeanParams from null PageAwareBeanParams"() {
		when:
		SeriesRestBeanParams seriesRestBeanParams = SeriesRestBeanParams.fromPageAwareBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
