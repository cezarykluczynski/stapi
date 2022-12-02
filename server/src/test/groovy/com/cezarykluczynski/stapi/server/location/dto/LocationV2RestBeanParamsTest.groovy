package com.cezarykluczynski.stapi.server.location.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class LocationV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates LocationV2RestBeanParams from PageSortBeanParams"() {
		when:
		LocationV2RestBeanParams locationV2RestBeanParams = LocationV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		locationV2RestBeanParams.pageNumber == PAGE_NUMBER
		locationV2RestBeanParams.pageSize == PAGE_SIZE
		locationV2RestBeanParams.sort == SORT
	}

	void "creates null LocationV2RestBeanParams from null PageSortBeanParams"() {
		when:
		LocationV2RestBeanParams locationV2RestBeanParams = LocationV2RestBeanParams.fromPageSortBeanParams null

		then:
		locationV2RestBeanParams == null
	}

}
