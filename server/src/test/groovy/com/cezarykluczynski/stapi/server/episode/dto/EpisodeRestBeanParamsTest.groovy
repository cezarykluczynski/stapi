package com.cezarykluczynski.stapi.server.episode.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class EpisodeRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates EpisodeRestBeanParams from PageSortBeanParams"() {
		when:
		EpisodeRestBeanParams episodeRestBeanParams = EpisodeRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		episodeRestBeanParams.pageNumber == PAGE_NUMBER
		episodeRestBeanParams.pageSize == PAGE_SIZE
		episodeRestBeanParams.sort == SORT
	}

	void "creates null EpisodeRestBeanParams from null PageSortBeanParams"() {
		when:
		EpisodeRestBeanParams seriesRestBeanParams = EpisodeRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
