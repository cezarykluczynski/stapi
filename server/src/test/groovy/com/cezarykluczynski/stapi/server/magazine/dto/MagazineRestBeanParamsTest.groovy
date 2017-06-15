package com.cezarykluczynski.stapi.server.magazine.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class MagazineRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates MagazineRestBeanParams from PageSortBeanParams"() {
		when:
		MagazineRestBeanParams magazineRestBeanParams = MagazineRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		magazineRestBeanParams.pageNumber == PAGE_NUMBER
		magazineRestBeanParams.pageSize == PAGE_SIZE
		magazineRestBeanParams.sort == SORT
	}

	void "creates null MagazineRestBeanParams from null PageSortBeanParams"() {
		when:
		MagazineRestBeanParams seriesRestBeanParams = MagazineRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
