package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import spock.lang.Specification

class ComicSeriesPublishedDateFixedValueProviderTest extends Specification {

	private ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProvider

	void setup() {
		comicSeriesPublishedDateFixedValueProvider = new ComicSeriesPublishedDateFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue('Star Trek Fotonovels').found
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue('Star Trek Fotonovels').value.from.year == 1977
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue('Star Trek Fotonovels').value.to.year == 1978
	}

	void "provides missing range"() {
		expect:
		!comicSeriesPublishedDateFixedValueProvider.getSearchedValue('Not found').found
		comicSeriesPublishedDateFixedValueProvider.getSearchedValue('Not found').value == null
	}

}
