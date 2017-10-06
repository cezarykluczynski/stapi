package com.cezarykluczynski.stapi.etl.species.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesPageFilterTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CategorySortingService categorySortingServiceMock

	private SpeciesPageFilter speciesTemplateFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		categorySortingServiceMock = Mock()
		speciesTemplateFilter = new SpeciesPageFilter(categoryTitlesExtractingProcessorMock, categorySortingServiceMock)
	}

	void "return true when page is a product of redirect"() {
		given:
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		result
	}

	void "return true when page title is 'Endangered species'"() {
		given:
		Page page = new Page(title: PageTitle.ENDANGERED_SPECIES)

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		result
	}

	void "returns true when any of the categories starts with 'Unnamed'"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(categories: categoryHeaderList)

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.UNNAMED_SPECIES)
		0 * _
		result
	}

	void "returns true when 'Lists' category is present"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(categories: categoryHeaderList)

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.LISTS)
		0 * _
		result
	}

	void "returns true when 'Biology' category is present"() {
		given:
		CategoryHeader categoryHeader = Mock()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(categoryHeader)
		Page page = new Page(categories: categoryHeaderList)

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.BIOLOGY)
		0 * _
		result
	}

	void "returns result of CategorySortingService when filtering could not be based on categories"() {
		given:
		boolean sortedOnTop = RandomUtil.nextBoolean()
		Page page = new Page(categories: Lists.newArrayList())

		when:
		boolean result = speciesTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, SpeciesPageFilter.SPECIES_CATEGORIES) >> sortedOnTop
		0 * _
		result == sortedOnTop
	}

}
