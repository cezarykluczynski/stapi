package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplateNumberOfBooksFixedValueProvider
import spock.lang.Specification

class BookSeriesTemplateNumberOfBooksFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Which Way Books'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private BookSeriesTemplateNumberOfBooksFixedValueProvider bookSeriesTemplateNumberOfBooksFixedValueProvider

	void setup() {
		bookSeriesTemplateNumberOfBooksFixedValueProvider = new BookSeriesTemplateNumberOfBooksFixedValueProvider()
	}

	void "provides correct number of books"() {
		expect:
		bookSeriesTemplateNumberOfBooksFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		bookSeriesTemplateNumberOfBooksFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 2
	}

	void "provides missing number of books"() {
		expect:
		!bookSeriesTemplateNumberOfBooksFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		bookSeriesTemplateNumberOfBooksFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
