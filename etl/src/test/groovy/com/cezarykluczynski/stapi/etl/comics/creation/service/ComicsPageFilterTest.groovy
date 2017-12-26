package com.cezarykluczynski.stapi.etl.comics.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ComicsPageFilter comicsPageFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		comicsPageFilter = new ComicsPageFilter(categoryTitlesExtractingProcessorMock)
	}

	void "returns true when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(ComicsPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = comicsPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when 'Star_Trek_series_magazines' is among page categories"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)

		when:
		boolean shouldBeFilteredOut = comicsPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.STAR_TREK_SERIES_MAGAZINES)
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = comicsPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when everything is in order"() {
		given:
		Page page = new Page(redirectPath: Lists.newArrayList())

		when:
		boolean shouldBeFilteredOut = comicsPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		0 * _
		!shouldBeFilteredOut
	}

}
