package com.cezarykluczynski.stapi.etl.book.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class BookPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private BookPageFilter bookPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		bookPageFilter = new BookPageFilter(categorySortingServiceMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns true when page is in comics-related categories"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List< CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(categories: categoryHeaderList)

		when:
		boolean shouldBeFilteredOut = bookPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> ['Comics']
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = bookPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = bookPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(BookPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = bookPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = bookPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
