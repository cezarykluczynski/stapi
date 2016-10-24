package com.cezarykluczynski.stapi.server.series.dto

import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams
import spock.lang.Specification

class SeriesRestBeanParamsTest extends Specification {

	private static final Integer PAGE_NUMBER = 1
	private static final Integer PAGE_SIZE = 20

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
