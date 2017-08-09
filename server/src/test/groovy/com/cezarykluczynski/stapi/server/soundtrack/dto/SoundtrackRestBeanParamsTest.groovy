package com.cezarykluczynski.stapi.server.soundtrack.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class SoundtrackRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates SoundtrackRestBeanParams from PageSortBeanParams"() {
		when:
		SoundtrackRestBeanParams soundtrackRestBeanParams = SoundtrackRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		soundtrackRestBeanParams.pageNumber == PAGE_NUMBER
		soundtrackRestBeanParams.pageSize == PAGE_SIZE
		soundtrackRestBeanParams.sort == SORT
	}

	void "creates null SoundtrackRestBeanParams from null PageSortBeanParams"() {
		when:
		SoundtrackRestBeanParams seriesRestBeanParams = SoundtrackRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
