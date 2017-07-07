package com.cezarykluczynski.stapi.server.video_release.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class VideoReleaseRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates VideoReleaseRestBeanParams from PageSortBeanParams"() {
		when:
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = VideoReleaseRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		videoReleaseRestBeanParams.pageNumber == PAGE_NUMBER
		videoReleaseRestBeanParams.pageSize == PAGE_SIZE
		videoReleaseRestBeanParams.sort == SORT
	}

	void "creates null VideoReleaseRestBeanParams from null PageSortBeanParams"() {
		when:
		VideoReleaseRestBeanParams seriesRestBeanParams = VideoReleaseRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
