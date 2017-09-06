package com.cezarykluczynski.stapi.server.spacecraft.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpacecraftRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpacecraftRestBeanParams from PageSortBeanParams"() {
		when:
		SpacecraftRestBeanParams spacecraftRestBeanParams = SpacecraftRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		spacecraftRestBeanParams.pageNumber == PAGE_NUMBER
		spacecraftRestBeanParams.pageSize == PAGE_SIZE
		spacecraftRestBeanParams.sort == SORT
	}

	void "creates null SpacecraftRestBeanParams from null PageSortBeanParams"() {
		when:
		SpacecraftRestBeanParams seriesRestBeanParams = SpacecraftRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
