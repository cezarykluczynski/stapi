package com.cezarykluczynski.stapi.server.technology.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TechnologyRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TechnologyRestBeanParams from PageSortBeanParams"() {
		when:
		TechnologyRestBeanParams technologyRestBeanParams = TechnologyRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		technologyRestBeanParams.pageNumber == PAGE_NUMBER
		technologyRestBeanParams.pageSize == PAGE_SIZE
		technologyRestBeanParams.sort == SORT
	}

	void "creates null TechnologyRestBeanParams from null PageSortBeanParams"() {
		when:
		TechnologyRestBeanParams seriesRestBeanParams = TechnologyRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
