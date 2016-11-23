package com.cezarykluczynski.stapi.server.performer.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams

class PerformerRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	def "creates PerformerRestBeanParams from PageAwareBeanParams"() {
		when:
		PerformerRestBeanParams performerRestBeanParams = PerformerRestBeanParams.fromPageAwareBeanParams(new PageAwareBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE
		))

		then:
		performerRestBeanParams.pageNumber == PAGE_NUMBER
		performerRestBeanParams.pageSize == PAGE_SIZE
	}

	def "creates null PerformerRestBeanParams from null PageAwareBeanParams"() {
		when:
		PerformerRestBeanParams seriesRestBeanParams = PerformerRestBeanParams.fromPageAwareBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
