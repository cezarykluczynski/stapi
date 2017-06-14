package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.book.processor.BookPublishedDateFixedValueProvider
import spock.lang.Specification

class BookPublishedDateFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Enterprise: Role Play Game In Star Trek'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private BookPublishedDateFixedValueProvider bookPublishedDateFixedValueProvider

	void setup() {
		bookPublishedDateFixedValueProvider = new BookPublishedDateFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		bookPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		bookPublishedDateFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.year == 1983
	}

	void "provides missing range"() {
		expect:
		!bookPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		bookPublishedDateFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
