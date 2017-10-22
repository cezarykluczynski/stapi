package com.cezarykluczynski.stapi.etl.occupation.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class OccupationPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private OccupationPageFilter occupationPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		occupationPageFilter = new OccupationPageFilter(categorySortingServiceMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(OccupationPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when 'Lists' category is found"() {
		given:
		CategoryHeader categoryHeader = Mock()
		Page page = new Page(categories: Lists.newArrayList(categoryHeader))

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList(categoryHeader)) >> Lists.newArrayList(CategoryTitle.LISTS)
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category, and no 'Lists' category is found"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		0 * _
		!shouldBeFilteredOut
	}

}
