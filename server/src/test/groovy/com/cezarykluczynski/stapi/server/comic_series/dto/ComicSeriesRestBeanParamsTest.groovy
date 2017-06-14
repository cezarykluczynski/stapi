package com.cezarykluczynski.stapi.server.comic_series.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ComicSeriesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ComicSeriesRestBeanParams from PageSortBeanParams"() {
		when:
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = ComicSeriesRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		comicSeriesRestBeanParams.pageNumber == PAGE_NUMBER
		comicSeriesRestBeanParams.pageSize == PAGE_SIZE
		comicSeriesRestBeanParams.sort == SORT
	}

	void "creates null ComicSeriesRestBeanParams from null PageSortBeanParams"() {
		when:
		ComicSeriesRestBeanParams seriesRestBeanParams = ComicSeriesRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
