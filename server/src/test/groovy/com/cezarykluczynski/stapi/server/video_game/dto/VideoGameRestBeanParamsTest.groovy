package com.cezarykluczynski.stapi.server.video_game.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class VideoGameRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates VideoGameRestBeanParams from PageSortBeanParams"() {
		when:
		VideoGameRestBeanParams videoGameRestBeanParams = VideoGameRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		videoGameRestBeanParams.pageNumber == PAGE_NUMBER
		videoGameRestBeanParams.pageSize == PAGE_SIZE
		videoGameRestBeanParams.sort == SORT
	}

	void "creates null VideoGameRestBeanParams from null PageSortBeanParams"() {
		when:
		VideoGameRestBeanParams seriesRestBeanParams = VideoGameRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
