package com.cezarykluczynski.stapi.server.comic_collection.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class ComicCollectionRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates ComicCollectionRestBeanParams from PageSortBeanParams"() {
		when:
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = ComicCollectionRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		comicCollectionRestBeanParams.pageNumber == PAGE_NUMBER
		comicCollectionRestBeanParams.pageSize == PAGE_SIZE
		comicCollectionRestBeanParams.sort == SORT
	}

	void "creates null ComicCollectionRestBeanParams from null PageSortBeanParams"() {
		when:
		ComicCollectionRestBeanParams seriesRestBeanParams = ComicCollectionRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
