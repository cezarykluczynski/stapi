package com.cezarykluczynski.stapi.etl.soundtrack.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class SoundtrackPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private SoundtrackPageFilter soundtrackFilter

	void setup() {
		categorySortingServiceMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		soundtrackFilter = new SoundtrackPageFilter(categorySortingServiceMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns true when page is sorted on top of invalid category"() {
		given:
		CategoryHeader categoryHeader = new CategoryHeader(title: RandomUtil
				.randomItem(SoundtrackPageFilter.INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF))
		Page page = new Page(categories: [categoryHeader])

		when:
		boolean shouldBeFilteredOut = soundtrackFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, SoundtrackPageFilter.INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page has invalid suffix"() {
		given:
		Page page = new Page(title: 'Star Trek: Picard (soundtracks)')

		when:
		boolean shouldBeFilteredOut = soundtrackFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when page is not sorted on top of invalid category and is not a list of soundtracks"() {
		given:
		Page page = new Page(title: 'Star Trek - Newly Recorded Music, Volume One')

		when:
		boolean shouldBeFilteredOut = soundtrackFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, SoundtrackPageFilter.INVALID_CATEGORIES_TO_BE_SORTED_ON_TOP_OF) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
