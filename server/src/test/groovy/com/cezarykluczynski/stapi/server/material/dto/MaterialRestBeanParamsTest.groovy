package com.cezarykluczynski.stapi.server.material.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class MaterialRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates MaterialRestBeanParams from PageSortBeanParams"() {
		when:
		MaterialRestBeanParams materialRestBeanParams = MaterialRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		materialRestBeanParams.pageNumber == PAGE_NUMBER
		materialRestBeanParams.pageSize == PAGE_SIZE
		materialRestBeanParams.sort == SORT
	}

	void "creates null MaterialRestBeanParams from null PageSortBeanParams"() {
		when:
		MaterialRestBeanParams seriesRestBeanParams = MaterialRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
