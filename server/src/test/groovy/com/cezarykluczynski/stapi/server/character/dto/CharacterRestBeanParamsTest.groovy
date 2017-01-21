package com.cezarykluczynski.stapi.server.character.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class CharacterRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates CharacterRestBeanParams from PageSortBeanParams"() {
		when:
		CharacterRestBeanParams characterRestBeanParams = CharacterRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		characterRestBeanParams.pageNumber == PAGE_NUMBER
		characterRestBeanParams.pageSize == PAGE_SIZE
		characterRestBeanParams.sort == SORT
	}

	void "creates null CharacterRestBeanParams from null PageSortBeanParams"() {
		when:
		CharacterRestBeanParams seriesRestBeanParams = CharacterRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
