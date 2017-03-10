package com.cezarykluczynski.stapi.server.species.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SpeciesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SpeciesRestBeanParams from PageSortBeanParams"() {
		when:
		SpeciesRestBeanParams speciesRestBeanParams = SpeciesRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		speciesRestBeanParams.pageNumber == PAGE_NUMBER
		speciesRestBeanParams.pageSize == PAGE_SIZE
		speciesRestBeanParams.sort == SORT
	}

	void "creates null SpeciesRestBeanParams from null PageSortBeanParams"() {
		when:
		SpeciesRestBeanParams seriesRestBeanParams = SpeciesRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
