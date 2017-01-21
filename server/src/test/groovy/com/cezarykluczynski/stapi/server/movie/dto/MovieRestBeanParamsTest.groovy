package com.cezarykluczynski.stapi.server.movie.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class MovieRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates MovieRestBeanParams from PageSortBeanParams"() {
		when:
		MovieRestBeanParams movieRestBeanParams = MovieRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		movieRestBeanParams.pageNumber == PAGE_NUMBER
		movieRestBeanParams.pageSize == PAGE_SIZE
		movieRestBeanParams.sort == SORT
	}

	void "creates null MovieRestBeanParams from null PageSortBeanParams"() {
		when:
		MovieRestBeanParams seriesRestBeanParams = MovieRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
