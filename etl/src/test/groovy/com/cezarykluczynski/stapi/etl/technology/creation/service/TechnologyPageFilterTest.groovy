package com.cezarykluczynski.stapi.etl.technology.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class TechnologyPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private TechnologyPageFilter technologyPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		technologyPageFilter = new TechnologyPageFilter(categorySortingServiceMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = technologyPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of technology-related categories"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = technologyPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, CategoryTitles.TECHNOLOGY) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(TechnologyPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = technologyPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = technologyPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, CategoryTitles.TECHNOLOGY) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
