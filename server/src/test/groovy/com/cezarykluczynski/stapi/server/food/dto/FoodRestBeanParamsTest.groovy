package com.cezarykluczynski.stapi.server.food.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class FoodRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates FoodRestBeanParams from PageSortBeanParams"() {
		when:
		FoodRestBeanParams foodRestBeanParams = FoodRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		foodRestBeanParams.pageNumber == PAGE_NUMBER
		foodRestBeanParams.pageSize == PAGE_SIZE
		foodRestBeanParams.sort == SORT
	}

	void "creates null FoodRestBeanParams from null PageSortBeanParams"() {
		when:
		FoodRestBeanParams seriesRestBeanParams = FoodRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
