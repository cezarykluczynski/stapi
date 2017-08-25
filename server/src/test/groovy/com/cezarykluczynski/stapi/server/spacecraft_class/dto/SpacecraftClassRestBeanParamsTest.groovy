package com.cezarykluczynski.stapi.server.spacecraft_class.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpacecraftClassRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpacecraftClassRestBeanParams from PageSortBeanParams"() {
		when:
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = SpacecraftClassRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		spacecraftClassRestBeanParams.pageNumber == PAGE_NUMBER
		spacecraftClassRestBeanParams.pageSize == PAGE_SIZE
		spacecraftClassRestBeanParams.sort == SORT
	}

	void "creates null SpacecraftClassRestBeanParams from null PageSortBeanParams"() {
		when:
		SpacecraftClassRestBeanParams seriesRestBeanParams = SpacecraftClassRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
