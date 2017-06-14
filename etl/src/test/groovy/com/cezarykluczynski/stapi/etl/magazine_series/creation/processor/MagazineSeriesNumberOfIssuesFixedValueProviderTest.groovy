package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.magazine_series.processor.MagazineSeriesNumberOfIssuesFixedValueProvider
import spock.lang.Specification

class MagazineSeriesNumberOfIssuesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek Fact Files'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private MagazineSeriesNumberOfIssuesFixedValueProvider magazineSeriesNumberOfIssuesFixedValueProvider

	void setup() {
		magazineSeriesNumberOfIssuesFixedValueProvider = new MagazineSeriesNumberOfIssuesFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		magazineSeriesNumberOfIssuesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		magazineSeriesNumberOfIssuesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 855
	}

	void "provides missing range"() {
		expect:
		!magazineSeriesNumberOfIssuesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		magazineSeriesNumberOfIssuesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
