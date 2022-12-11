package com.cezarykluczynski.stapi.server.company.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class CompanyV2RestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates CompanyRestBeanParams from PageSortBeanParams"() {
		when:
		CompanyV2RestBeanParams companyV2RestBeanParams = CompanyV2RestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		companyV2RestBeanParams.pageNumber == PAGE_NUMBER
		companyV2RestBeanParams.pageSize == PAGE_SIZE
		companyV2RestBeanParams.sort == SORT
	}

	void "creates null CompanyV2RestBeanParams from null PageSortBeanParams"() {
		when:
		CompanyV2RestBeanParams companyV2RestBeanParams = CompanyV2RestBeanParams.fromPageSortBeanParams null

		then:
		companyV2RestBeanParams == null
	}

}
