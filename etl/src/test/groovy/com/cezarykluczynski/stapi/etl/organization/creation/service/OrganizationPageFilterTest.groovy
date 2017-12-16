package com.cezarykluczynski.stapi.etl.organization.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class OrganizationPageFilterTest extends Specification {

	private static final String TITLE_BOTH_CASES = 'United Federation of Planets'

	private CategorySortingService categorySortingServiceMock

	private OrganizationNameFilter organizationNameFilterMock

	private OrganizationPageFilter organizationPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		organizationNameFilterMock = Mock()
		organizationPageFilter = new OrganizationPageFilter(categorySortingServiceMock, organizationNameFilterMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

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

	void "returns false when page title contains more then one word, all words starting with capital letter"() {
		given:
		String title = 'Air Defense Command'
		Page page = new Page(title: title)

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * organizationNameFilterMock.isAnOrganization(title) >> OrganizationNameFilter.Match.UNKNOWN_RESULT
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

	void "returns false when page title contains words starting with small letter and capital letter, and OrganizationNameFilter returns null"() {
		given:
		Page page = new Page(title: TITLE_BOTH_CASES)

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * organizationNameFilterMock.isAnOrganization(TITLE_BOTH_CASES) >> OrganizationNameFilter.Match.UNKNOWN_RESULT
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

	void "returns false when page title contains words starting with small letter and capital letter, and OrganizationNameFilter returns true"() {
		given:
		Page page = new Page(title: TITLE_BOTH_CASES)

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		1 * organizationNameFilterMock.isAnOrganization(TITLE_BOTH_CASES) >> OrganizationNameFilter.Match.IS_AN_ORGANIZATION
		0 * _
		!shouldBeFilteredOut
	}

	void "returns true when page title contains words starting with small letter and capital letter, and OrganizationNameFilter returns false"() {
		given:
		Page page = new Page(title: TITLE_BOTH_CASES)

		when:
		boolean shouldBeFilteredOut = organizationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		1 * organizationNameFilterMock.isAnOrganization(TITLE_BOTH_CASES) >> OrganizationNameFilter.Match.IS_NOT_AN_ORGANIZATION
		0 * _
		shouldBeFilteredOut
	}

}
