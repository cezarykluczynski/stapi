package com.cezarykluczynski.stapi.server.conflict.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ConflictRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ConflictRestBeanParams from PageSortBeanParams"() {
		when:
		ConflictRestBeanParams conflictRestBeanParams = ConflictRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		conflictRestBeanParams.pageNumber == PAGE_NUMBER
		conflictRestBeanParams.pageSize == PAGE_SIZE
		conflictRestBeanParams.sort == SORT
	}

	void "creates null ConflictRestBeanParams from null PageSortBeanParams"() {
		when:
		ConflictRestBeanParams seriesRestBeanParams = ConflictRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
