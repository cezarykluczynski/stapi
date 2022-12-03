package com.cezarykluczynski.stapi.etl.location.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class LocationPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private LocationPageFilter locationPageFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		locationPageFilter = new LocationPageFilter(categoryTitlesExtractingProcessorMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: [pageHeader])

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page contains invalid category"() {
		given:
		CategoryHeader categoryHeader = new CategoryHeader(title: RandomUtil.randomItem(CategoryTitles.ORGANIZATIONS))
		Page page = new Page(categories: [categoryHeader])

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([categoryHeader]) >> [categoryHeader.title]
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when LocationNameFilter returns null"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		0 * _
		!shouldBeFilteredOut
	}

}
