package com.cezarykluczynski.stapi.server.animal.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class AnimalRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates AnimalRestBeanParams from PageSortBeanParams"() {
		when:
		AnimalRestBeanParams animalRestBeanParams = AnimalRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		animalRestBeanParams.pageNumber == PAGE_NUMBER
		animalRestBeanParams.pageSize == PAGE_SIZE
		animalRestBeanParams.sort == SORT
	}

	void "creates null AnimalRestBeanParams from null PageSortBeanParams"() {
		when:
		AnimalRestBeanParams seriesRestBeanParams = AnimalRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
