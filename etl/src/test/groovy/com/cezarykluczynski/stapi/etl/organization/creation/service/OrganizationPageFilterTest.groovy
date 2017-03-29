package com.cezarykluczynski.stapi.etl.organization.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class OrganizationPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private OrganizationPageFilter organizationPageFilter

	void setup() {
		categorySortingServiceMock = Mock(CategorySortingService)
		organizationPageFilter = new OrganizationPageFilter(categorySortingServiceMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		Page page = new Page(redirectPath: Lists.newArrayList(Mock(PageHeader)))

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

}
