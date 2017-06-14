package com.cezarykluczynski.stapi.server.book_collection.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class BookCollectionRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates BookCollectionRestBeanParams from PageSortBeanParams"() {
		when:
		BookCollectionRestBeanParams bookCollectionRestBeanParams = BookCollectionRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		bookCollectionRestBeanParams.pageNumber == PAGE_NUMBER
		bookCollectionRestBeanParams.pageSize == PAGE_SIZE
		bookCollectionRestBeanParams.sort == SORT
	}

	void "creates null BookCollectionRestBeanParams from null PageSortBeanParams"() {
		when:
		BookCollectionRestBeanParams seriesRestBeanParams = BookCollectionRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
