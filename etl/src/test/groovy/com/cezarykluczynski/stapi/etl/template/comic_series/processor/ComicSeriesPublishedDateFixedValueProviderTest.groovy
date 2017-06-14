package com.cezarykluczynski.stapi.etl.template.comic_series.processor

import spock.lang.Specification

class ComicSeriesPublishedDateFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek Fotonovels'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProvider

	void setup() {
		comicSeriesPublishedDateFixedValueProvider = new ComicSeriesPublishedDateFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.from.year == 1977
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.to.year == 1978
	}

	void "provides missing range"() {
		expect:
		!comicSeriesPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
