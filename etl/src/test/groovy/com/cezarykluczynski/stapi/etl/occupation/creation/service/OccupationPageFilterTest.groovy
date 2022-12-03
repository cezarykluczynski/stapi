package com.cezarykluczynski.stapi.etl.occupation.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class OccupationPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategorySortingService categorySortingServiceMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private OccupationPageFilter occupationPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		occupationPageFilter = new OccupationPageFilter(categorySortingServiceMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
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
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title ends with suffix pointing to a group"() {
		given:
		Page page = new Page(title: "Some ${RandomUtil.randomItem(OccupationPageFilter.INVALID_SUFFIXES)}")

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when invalid category is found"() {
		given:
		CategoryHeader categoryHeader = new CategoryHeader(title: RandomUtil.randomItem(OccupationPageFilter.INVALID_CATEGORIES))
		Page page = new Page(title: TITLE, categories: Lists.newArrayList(categoryHeader))

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([categoryHeader]) >> [categoryHeader.title]
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category, and no 'Lists' category is found"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = occupationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		0 * _
		!shouldBeFilteredOut
	}

}
