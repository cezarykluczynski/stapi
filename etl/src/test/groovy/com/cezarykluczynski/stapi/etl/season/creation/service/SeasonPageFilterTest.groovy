package com.cezarykluczynski.stapi.etl.season.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class SeasonPageFilterTest extends Specification {

	private SeasonPageFilter seasonPageFilter

	void setup() {
		seasonPageFilter = new SeasonPageFilter()
	}

	void "returns true when page title starts with 'After Trek'"() {
		given:
		Page page = new Page(title: 'After Trek Season 1')

		when:
		boolean shouldBeFilteredOut = seasonPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns false with regular season title"() {
		given:
		Page page = new Page(title: 'DS9 Season 1')

		when:
		boolean shouldBeFilteredOut = seasonPageFilter.shouldBeFilteredOut(page)

		then:
		!shouldBeFilteredOut
	}

}
