package com.cezarykluczynski.stapi.etl.template.series.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class SeriesPageFilterTest extends Specification {

	private SeriesPageFilter seriesPageFilter

	void setup() {
		seriesPageFilter = new SeriesPageFilter()
	}

	void "returns true when page title is 'After Trek'"() {
		given:
		Page page = new Page(title: 'After Trek')

		when:
		boolean shouldBeFilteredOut = seriesPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns false with regular series title"() {
		given:
		Page page = new Page(title: 'Star Trek: Deep Space Nine')

		when:
		boolean shouldBeFilteredOut = seriesPageFilter.shouldBeFilteredOut(page)

		then:
		!shouldBeFilteredOut
	}

}
