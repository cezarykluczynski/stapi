package com.cezarykluczynski.stapi.server.title.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TitleRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TitleRestBeanParams from PageSortBeanParams"() {
		when:
		TitleRestBeanParams titleRestBeanParams = TitleRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		titleRestBeanParams.pageNumber == PAGE_NUMBER
		titleRestBeanParams.pageSize == PAGE_SIZE
		titleRestBeanParams.sort == SORT
	}

	void "creates null TitleRestBeanParams from null PageSortBeanParams"() {
		when:
		TitleRestBeanParams seriesRestBeanParams = TitleRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
