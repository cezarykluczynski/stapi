package com.cezarykluczynski.stapi.etl.template.comic_series.processor

import spock.lang.Specification

class ComicSeriesTemplateNumberOfIssuesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'McDonald\'s'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private ComicSeriesTemplateNumberOfIssuesFixedValueProvider comicSeriesTemplateNumberOfIssuesFixedValueProvider

	void setup() {
		comicSeriesTemplateNumberOfIssuesFixedValueProvider = new ComicSeriesTemplateNumberOfIssuesFixedValueProvider()
	}

	void "provides correct number of issues"() {
		expect:
		comicSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		comicSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 5
	}

	void "provides missing number of issues"() {
		expect:
		!comicSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		comicSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
