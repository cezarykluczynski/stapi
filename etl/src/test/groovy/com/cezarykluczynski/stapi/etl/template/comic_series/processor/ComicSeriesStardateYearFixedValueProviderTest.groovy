package com.cezarykluczynski.stapi.etl.template.comic_series.processor

import spock.lang.Specification

class ComicSeriesStardateYearFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek: The Official Motion Picture Adaptation'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private ComicSeriesStardateYearFixedValueProvider comicSeriesStardateYearFixedValueProvider

	void setup() {
		comicSeriesStardateYearFixedValueProvider = new ComicSeriesStardateYearFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		comicSeriesStardateYearFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		comicSeriesStardateYearFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.yearFrom == 2230
		comicSeriesStardateYearFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.yearTo == 2387
	}

	void "provides missing range"() {
		expect:
		!comicSeriesStardateYearFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		comicSeriesStardateYearFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
