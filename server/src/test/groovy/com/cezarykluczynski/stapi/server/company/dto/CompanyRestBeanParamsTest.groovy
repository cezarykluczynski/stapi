package com.cezarykluczynski.stapi.server.company.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class CompanyRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates CompanyRestBeanParams from PageSortBeanParams"() {
		when:
		CompanyRestBeanParams companyRestBeanParams = CompanyRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		companyRestBeanParams.pageNumber == PAGE_NUMBER
		companyRestBeanParams.pageSize == PAGE_SIZE
		companyRestBeanParams.sort == SORT
	}

	void "creates null CompanyRestBeanParams from null PageSortBeanParams"() {
		when:
		CompanyRestBeanParams seriesRestBeanParams = CompanyRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
