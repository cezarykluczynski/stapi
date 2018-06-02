package com.cezarykluczynski.stapi.etl.astronomical_object.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class AstronomicalObjectPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	AstronomicalObjectPageFilter astronomicalObjectPageFilter

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		astronomicalObjectPageFilter = new AstronomicalObjectPageFilter(categoryTitlesExtractingProcessorMock)

	}

	void "returns true when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		result
	}

	void "returns true when page is 'Unnamed planets'"() {
		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(title: 'Unnamed planets'))

		then:
		result
	}

	void "returns true when page is 'Planetary classification'"() {
		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(title: 'Planetary classification'))

		then:
		result
	}

	void "returns true when page 'Planet lists' category is present"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitle.PLANET_LISTS))

		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(
				title: TITLE,
				categories: categoryHeaderList
		))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.PLANET_LISTS)
		0 * _
		result
	}

	void "returns false when everything is in order"() {
		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(
				title: TITLE
		))

		then:
		1 * categoryTitlesExtractingProcessorMock.process([]) >> []
		0 * _
		!result
	}

}
