package com.cezarykluczynski.stapi.server.organization.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class OrganizationRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates OrganizationRestBeanParams from PageSortBeanParams"() {
		when:
		OrganizationRestBeanParams organizationRestBeanParams = OrganizationRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		organizationRestBeanParams.pageNumber == PAGE_NUMBER
		organizationRestBeanParams.pageSize == PAGE_SIZE
		organizationRestBeanParams.sort == SORT
	}

	void "creates null OrganizationRestBeanParams from null PageSortBeanParams"() {
		when:
		OrganizationRestBeanParams seriesRestBeanParams = OrganizationRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
