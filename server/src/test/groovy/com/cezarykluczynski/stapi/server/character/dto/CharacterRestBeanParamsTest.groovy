package com.cezarykluczynski.stapi.server.character.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams

class CharacterRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	def "creates CharacterRestBeanParams from PageAwareBeanParams"() {
		when:
		CharacterRestBeanParams characterRestBeanParams = CharacterRestBeanParams.fromPageAwareBeanParams(new PageAwareBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE
		))

		then:
		characterRestBeanParams.pageNumber == PAGE_NUMBER
		characterRestBeanParams.pageSize == PAGE_SIZE
	}

	def "creates null CharacterRestBeanParams from null PageAwareBeanParams"() {
		when:
		CharacterRestBeanParams seriesRestBeanParams = CharacterRestBeanParams.fromPageAwareBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
