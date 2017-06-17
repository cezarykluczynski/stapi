package com.cezarykluczynski.stapi.server.literature.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class LiteratureRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates LiteratureRestBeanParams from PageSortBeanParams"() {
		when:
		LiteratureRestBeanParams literatureRestBeanParams = LiteratureRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		literatureRestBeanParams.pageNumber == PAGE_NUMBER
		literatureRestBeanParams.pageSize == PAGE_SIZE
		literatureRestBeanParams.sort == SORT
	}

	void "creates null LiteratureRestBeanParams from null PageSortBeanParams"() {
		when:
		LiteratureRestBeanParams seriesRestBeanParams = LiteratureRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
