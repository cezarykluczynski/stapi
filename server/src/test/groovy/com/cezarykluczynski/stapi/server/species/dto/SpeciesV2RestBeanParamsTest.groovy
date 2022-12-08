package com.cezarykluczynski.stapi.server.species.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpeciesV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpeciesV2RestBeanParams from PageSortBeanParams"() {
		when:
		SpeciesV2RestBeanParams speciesV2RestBeanParams = SpeciesV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		speciesV2RestBeanParams.pageNumber == PAGE_NUMBER
		speciesV2RestBeanParams.pageSize == PAGE_SIZE
		speciesV2RestBeanParams.sort == SORT
	}

	void "creates null SpeciesV2RestBeanParams from null PageSortBeanParams"() {
		when:
		SpeciesV2RestBeanParams speciesV2RestBeanParams = SpeciesV2RestBeanParams.fromPageSortBeanParams null

		then:
		speciesV2RestBeanParams == null
	}

}
