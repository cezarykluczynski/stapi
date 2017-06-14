package com.cezarykluczynski.stapi.server.astronomical_object.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class AstronomicalObjectRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates AstronomicalObjectRestBeanParams from PageSortBeanParams"() {
		when:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = AstronomicalObjectRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		astronomicalObjectRestBeanParams.pageNumber == PAGE_NUMBER
		astronomicalObjectRestBeanParams.pageSize == PAGE_SIZE
		astronomicalObjectRestBeanParams.sort == SORT
	}

	void "creates null AstronomicalObjectRestBeanParams from null PageSortBeanParams"() {
		when:
		AstronomicalObjectRestBeanParams seriesRestBeanParams = AstronomicalObjectRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
