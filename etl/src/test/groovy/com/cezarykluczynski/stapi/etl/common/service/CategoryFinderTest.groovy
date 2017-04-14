package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class CategoryFinderTest extends Specification {

	private static final String CATEGORY_1 = 'CATEGORY_1'
	private static final String CATEGORY_2 = 'CATEGORY_2'
	private static final String CATEGORY_3 = 'CATEGORY_3'
	private static final String CATEGORY_4 = 'CATEGORY_4'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CategoryFinder categoryFinder

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		categoryFinder = new CategoryFinder(categoryTitlesExtractingProcessorMock)
	}

	void "returns true when page has any of the given categories"() {
		given:
		List<CategoryHeader> pageCategoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CATEGORY_1),
				new CategoryHeader(title: CATEGORY_2),
				new CategoryHeader(title: CATEGORY_3))
		List<String> pageCategoryList = Lists.newArrayList(CATEGORY_1, CATEGORY_2, CATEGORY_3)
		Page page = new Page(categories: pageCategoryHeaderList)
		List<String> categoryList = Lists.newArrayList(CATEGORY_4, CATEGORY_3)

		when:
		boolean result = categoryFinder.hasAnyCategory(page, categoryList)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(pageCategoryHeaderList) >> pageCategoryList
		0 * _
		result
	}

	void "returns false when page does not have any of the given categories"() {
		given:
		List<CategoryHeader> pageCategoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CATEGORY_1),
				new CategoryHeader(title: CATEGORY_2))
		List<String> pageCategoryList = Lists.newArrayList(CATEGORY_1, CATEGORY_2)
		Page page = new Page(categories: pageCategoryHeaderList)
		List<String> categoryList = Lists.newArrayList(CATEGORY_4, CATEGORY_3)

		when:
		boolean result = categoryFinder.hasAnyCategory(page, categoryList)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(pageCategoryHeaderList) >> pageCategoryList
		0 * _
		!result
	}

}
