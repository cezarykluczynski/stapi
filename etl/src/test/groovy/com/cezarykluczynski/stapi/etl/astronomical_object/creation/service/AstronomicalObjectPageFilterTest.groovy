package com.cezarykluczynski.stapi.etl.astronomical_object.creation.service

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class AstronomicalObjectPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	AstronomicalObjectPageFilter astronomicalObjectPageFilter

	void setup() {
		astronomicalObjectPageFilter = new AstronomicalObjectPageFilter(new CategoryTitlesExtractingProcessor())

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

	void "returns true when page name is among invalid titled"() {
		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(
				title: RandomUtil.randomItem(AstronomicalObjectPageFilter.INVALID_TITLES)))

		then:
		result
	}

	void "returns true when page has invalid category"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(
				title: RandomUtil.randomItem(AstronomicalObjectPageFilter.INVALID_CATEGORIES)))

		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(
				title: TITLE,
				categories: categoryHeaderList
		))

		then:
		0 * _
		result
	}

	void "returns false when everything is in order"() {
		when:
		boolean result = astronomicalObjectPageFilter.shouldBeFilteredOut(new Page(
				title: TITLE
		))

		then:
		0 * _
		!result
	}

}
