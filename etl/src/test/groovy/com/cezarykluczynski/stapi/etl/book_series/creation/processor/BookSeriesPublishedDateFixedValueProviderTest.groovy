package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesPublishedDateFixedValueProvider
import spock.lang.Specification

class BookSeriesPublishedDateFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek: Prometheus'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private BookSeriesPublishedDateFixedValueProvider bookSeriesPublishedDateFixedValueProvider

	void setup() {
		bookSeriesPublishedDateFixedValueProvider = new BookSeriesPublishedDateFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.from.month == 7
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.from.year == 2016
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.to.month == 9
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.to.year == 2016
	}

	void "provides missing range"() {
		expect:
		!bookSeriesPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		bookSeriesPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
