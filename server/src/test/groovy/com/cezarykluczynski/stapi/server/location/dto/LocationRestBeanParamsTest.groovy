package com.cezarykluczynski.stapi.server.location.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class LocationRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates LocationRestBeanParams from PageSortBeanParams"() {
		when:
		LocationRestBeanParams locationRestBeanParams = LocationRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		locationRestBeanParams.pageNumber == PAGE_NUMBER
		locationRestBeanParams.pageSize == PAGE_SIZE
		locationRestBeanParams.sort == SORT
	}

	void "creates null LocationRestBeanParams from null PageSortBeanParams"() {
		when:
		LocationRestBeanParams seriesRestBeanParams = LocationRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
