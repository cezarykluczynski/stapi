package com.cezarykluczynski.stapi.server.season.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SeasonRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SeasonRestBeanParams from PageSortBeanParams"() {
		when:
		SeasonRestBeanParams seasonRestBeanParams = SeasonRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		seasonRestBeanParams.pageNumber == PAGE_NUMBER
		seasonRestBeanParams.pageSize == PAGE_SIZE
		seasonRestBeanParams.sort == SORT
	}

	void "creates null SeasonRestBeanParams from null PageSortBeanParams"() {
		when:
		SeasonRestBeanParams seriesRestBeanParams = SeasonRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
