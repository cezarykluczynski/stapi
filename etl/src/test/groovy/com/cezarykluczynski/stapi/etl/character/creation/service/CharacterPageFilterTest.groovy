package com.cezarykluczynski.stapi.etl.character.creation.service

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

class CharacterPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = 'WIKITEXT'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private CategorySortingService categorySortingServiceMock

	private CharacterPageFilter characterPageFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		categorySortingServiceMock = Mock()
		characterPageFilter = new CharacterPageFilter(categoryTitlesExtractingProcessorMock, categorySortingServiceMock)
	}

	void "returns true when page name starts with 'Unnamed '"() {
		given:
		Page page = new Page(
				title: 'Unnamed humanoids',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page name starts with 'List of '"() {
		given:
		Page page = new Page(
				title: 'List of some people',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page name starts with 'Memory Alpha images '"() {
		given:
		Page page = new Page(
				title: 'Memory Alpha images (Greek gods)',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page name contains 'personnel'"() {
		given:
		Page page = new Page(
				title: PageTitle.MEMORY_ALPHA_PERSONNEL,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when category list contains Production lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryTitle.PRODUCTION_LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PRODUCTION_LISTS)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Families"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryTitle.FAMILIES))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.FAMILIES)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Personnel lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryTitle.PERSONNEL_LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PERSONNEL_LISTS)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryTitle.LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.LISTS)
		shouldBeFilteredOut
	}

	void "returns true when category list contains category that start with 'Unnamed'"() {
		given:
		String categoryTitle = 'Unnamed Klingons'
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: categoryTitle))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(categoryTitle)
		shouldBeFilteredOut
	}

	void "returns true whe page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		shouldBeFilteredOut
	}

	void "returns results of CategorySortingService call when no other conditions were met"() {
		given:
		boolean sortedOnTop = RandomUtil.nextBoolean()
		Page page = new Page(
				title: TITLE,
				wikitext: WIKITEXT,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = characterPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> sortedOnTop
		shouldBeFilteredOut == sortedOnTop
	}

}
