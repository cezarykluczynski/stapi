package com.cezarykluczynski.stapi.etl.performer.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class ActorPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ActorPageFilter actorPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		actorPageFilter = new ActorPageFilter(categorySortingServiceMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = actorPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(ActorPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = actorPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when invalid category is found"() {
		given:
		CategoryHeader categoryHeader = new CategoryHeader(title: RandomUtil.randomItem(ActorPageFilter.INVALID_CATEGORIES))
		Page page = new Page(categories: Lists.newArrayList(categoryHeader))

		when:
		boolean shouldBeFilteredOut = actorPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([categoryHeader]) >> [categoryHeader.title]
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = actorPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = actorPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
