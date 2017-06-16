package com.cezarykluczynski.stapi.server.magazine_series.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class MagazineSeriesRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates MagazineSeriesRestBeanParams from PageSortBeanParams"() {
		when:
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = MagazineSeriesRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		magazineSeriesRestBeanParams.pageNumber == PAGE_NUMBER
		magazineSeriesRestBeanParams.pageSize == PAGE_SIZE
		magazineSeriesRestBeanParams.sort == SORT
	}

	void "creates null MagazineSeriesRestBeanParams from null PageSortBeanParams"() {
		when:
		MagazineSeriesRestBeanParams seriesRestBeanParams = MagazineSeriesRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
