package com.cezarykluczynski.stapi.server.spacecraft.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpacecraftV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpacecraftV2RestBeanParams from PageSortBeanParams"() {
		when:
		SpacecraftV2RestBeanParams spacecraftV2RestBeanParams = SpacecraftV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		spacecraftV2RestBeanParams.pageNumber == PAGE_NUMBER
		spacecraftV2RestBeanParams.pageSize == PAGE_SIZE
		spacecraftV2RestBeanParams.sort == SORT
	}

	void "creates null SpacecraftV2RestBeanParams from null PageSortBeanParams"() {
		when:
		SpacecraftV2RestBeanParams spacecraftV2RestBeanParams = SpacecraftV2RestBeanParams.fromPageSortBeanParams null

		then:
		spacecraftV2RestBeanParams == null
	}

}
