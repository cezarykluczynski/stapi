package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class SeasonPageFilterTest extends Specification {

	private SeasonPageFilter seasonPageFilter

	void setup() {
		seasonPageFilter = new SeasonPageFilter()
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(SeasonPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = seasonPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title is not among invalid titles"() {
		given:
		Page page = new Page(title: 'DS9 Season 3')

		when:
		boolean shouldBeFilteredOut = seasonPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		!shouldBeFilteredOut
	}

}
