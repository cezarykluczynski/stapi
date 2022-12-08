package com.cezarykluczynski.stapi.server.technology.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TechnologyV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TechnologyV2RestBeanParams from PageSortBeanParams"() {
		when:
		TechnologyV2RestBeanParams technologyRestBeanParams = TechnologyV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		technologyRestBeanParams.pageNumber == PAGE_NUMBER
		technologyRestBeanParams.pageSize == PAGE_SIZE
		technologyRestBeanParams.sort == SORT
	}

	void "creates null TechnologyV2RestBeanParams from null PageSortBeanParams"() {
		when:
		TechnologyV2RestBeanParams technologyRestBeanParams = TechnologyV2RestBeanParams.fromPageSortBeanParams null

		then:
		technologyRestBeanParams == null
	}

}
