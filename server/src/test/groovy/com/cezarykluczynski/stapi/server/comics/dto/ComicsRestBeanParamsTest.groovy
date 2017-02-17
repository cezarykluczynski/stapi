package com.cezarykluczynski.stapi.server.comics.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ComicsRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ComicsRestBeanParams from PageSortBeanParams"() {
		when:
		ComicsRestBeanParams comicsRestBeanParams = ComicsRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		comicsRestBeanParams.pageNumber == PAGE_NUMBER
		comicsRestBeanParams.pageSize == PAGE_SIZE
		comicsRestBeanParams.sort == SORT
	}

	void "creates null ComicsRestBeanParams from null PageSortBeanParams"() {
		when:
		ComicsRestBeanParams seriesRestBeanParams = ComicsRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
