package com.cezarykluczynski.stapi.server.spacecraft_class.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpacecraftClassV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpacecraftClassV2RestBeanParams from PageSortBeanParams"() {
		when:
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = SpacecraftClassV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		spacecraftClassV2RestBeanParams.pageNumber == PAGE_NUMBER
		spacecraftClassV2RestBeanParams.pageSize == PAGE_SIZE
		spacecraftClassV2RestBeanParams.sort == SORT
	}

	void "creates null SpacecraftClassV2RestBeanParams from null PageSortBeanParams"() {
		when:
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = SpacecraftClassV2RestBeanParams.fromPageSortBeanParams null

		then:
		spacecraftClassV2RestBeanParams == null
	}

}
