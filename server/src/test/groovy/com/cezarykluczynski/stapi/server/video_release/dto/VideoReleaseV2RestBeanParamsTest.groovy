package com.cezarykluczynski.stapi.server.video_release.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class VideoReleaseV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates VideoReleaseRestBeanParams from PageSortBeanParams"() {
		when:
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = VideoReleaseV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		videoReleaseV2RestBeanParams.pageNumber == PAGE_NUMBER
		videoReleaseV2RestBeanParams.pageSize == PAGE_SIZE
		videoReleaseV2RestBeanParams.sort == SORT
	}

	void "creates null VideoReleaseV2RestBeanParams from null PageSortBeanParams"() {
		when:
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = VideoReleaseV2RestBeanParams.fromPageSortBeanParams null

		then:
		videoReleaseV2RestBeanParams == null
	}

}
