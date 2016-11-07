package com.cezarykluczynski.stapi.server.staff.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams

class StaffRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	def "creates StaffRestBeanParams from PageAwareBeanParams"() {
		when:
		StaffRestBeanParams seriesRestBeanParams = StaffRestBeanParams.fromPageAwareBeanParams(new PageAwareBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE
		))

		then:
		seriesRestBeanParams.pageNumber == PAGE_NUMBER
		seriesRestBeanParams.pageSize == PAGE_SIZE
	}

	def "creates null StaffRestBeanParams from null PageAwareBeanParams"() {
		when:
		StaffRestBeanParams seriesRestBeanParams = StaffRestBeanParams.fromPageAwareBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
