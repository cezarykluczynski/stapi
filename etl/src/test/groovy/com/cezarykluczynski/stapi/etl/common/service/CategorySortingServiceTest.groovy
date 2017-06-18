package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class CategorySortingServiceTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String VALID_CATEGORY = 'VALID_CATEGORY'
	private static final String INVALID_CATEGORY = 'INVALID_CATEGORY'

	private WikitextApi wikitextApiMock

	private CategorySortingService categorySortingService

	void setup() {
		wikitextApiMock = Mock()
		categorySortingService = new CategorySortingService(wikitextApiMock)
	}

	void "returns true when category is on top of any category, sorted with empty page link description"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageLink pageLink = new PageLink(
				title: 'Category:Name',
				description: '')

		when:
		boolean result = categorySortingService.isSortedOnTopOfAnyCategory(page)

		then:
		1 *  wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink)
		0 * _
		result
	}

	void "returns true when category is on top of any category, sorted with starting space"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageLink pageLink = new PageLink(
				title: 'Category:Name',
				description: '03',
				untrimmedDescription: ' 03')

		when:
		boolean result = categorySortingService.isSortedOnTopOfAnyCategory(page)

		then:
		1 *  wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink)
		0 * _
		result
	}

	void "returns false when category is not on top of any category"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageLink pageLink = new PageLink(
				title: 'Category:Name',
				description: 'Name')

		when:
		boolean result = categorySortingService.isSortedOnTopOfAnyCategory(page)

		then:
		1 *  wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink)
		0 * _
		!result
	}

	void "returns true when category is on top of any of the given categories"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageLink pageLink1 = new PageLink(
				title: "Category:${INVALID_CATEGORY}",
				description: '')
		PageLink pageLink2 = new PageLink(
				title: "Category:${VALID_CATEGORY}",
				description: '')

		when:
		boolean result = categorySortingService.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList('Some_other_category', VALID_CATEGORY))

		then:
		1 *  wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink1, pageLink2)
		0 * _
		result
	}

	void "returns false when category is not on top of any of the given categories"() {
		given:
		Page page = new Page(wikitext: WIKITEXT)
		PageLink pageLink1 = new PageLink(title: "Category:${INVALID_CATEGORY}", description: '')

		when:
		boolean result = categorySortingService.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(VALID_CATEGORY))

		then:
		1 *  wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink1)
		0 * _
		!result
	}

}
