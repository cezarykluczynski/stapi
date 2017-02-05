package com.cezarykluczynski.stapi.etl.template.individual.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.constant.PageName
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualTemplateFilterTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = 'WIKITEXT'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private WikitextApi wikitextApiMock

	private IndividualTemplateFilter individualTemplateFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock(CategoryTitlesExtractingProcessor)
		wikitextApiMock = Mock(WikitextApi)
		individualTemplateFilter = new IndividualTemplateFilter(categoryTitlesExtractingProcessorMock, wikitextApiMock)
	}

	void "returns true when page name starts with 'Unnamed '"() {
		given:
		Page page = new Page(
				title: 'Unnamed humanoids',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

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
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

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
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page name contains 'personnel'"() {
		given:
		Page page = new Page(
				title: PageName.MEMORY_ALPHA_PERSONNEL,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when category list contains Production lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryName.PRODUCTION_LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.PRODUCTION_LISTS)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Families"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryName.FAMILIES))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.FAMILIES)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Personnel lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryName.PERSONNEL_LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.PERSONNEL_LISTS)
		shouldBeFilteredOut
	}

	void "returns true when category list contains Lists"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryName.LISTS))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.LISTS)
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
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(categoryTitle)
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page(
				title: TITLE,
				wikitext: WIKITEXT,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(
				new PageLink(
						title: 'Category:Some page'
				),
				new PageLink(
						title: 'category:Some other page',
						description: StringUtils.EMPTY
				),
				new PageLink(
						title: 'category:Yet another page',
						description: 'Page, yet another'
				)
		)
		shouldBeFilteredOut
	}

	void "returns false when there is no categories and no links in wikitext"() {
		given:
		Page page = new Page(
				title: TITLE,
				wikitext: WIKITEXT,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = individualTemplateFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		!shouldBeFilteredOut
	}

}
