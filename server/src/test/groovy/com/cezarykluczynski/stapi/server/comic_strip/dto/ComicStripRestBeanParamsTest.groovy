package com.cezarykluczynski.stapi.server.comic_strip.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ComicStripRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ComicStripRestBeanParams from PageSortBeanParams"() {
		when:
		ComicStripRestBeanParams comicStripRestBeanParams = ComicStripRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		comicStripRestBeanParams.pageNumber == PAGE_NUMBER
		comicStripRestBeanParams.pageSize == PAGE_SIZE
		comicStripRestBeanParams.sort == SORT
	}

	void "creates null ComicStripRestBeanParams from null PageSortBeanParams"() {
		when:
		ComicStripRestBeanParams seriesRestBeanParams = ComicStripRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
