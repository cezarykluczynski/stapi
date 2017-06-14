package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.etl.template.magazine_series.processor.MagazineSeriesPublicationDatesFixedValueProvider
import spock.lang.Specification

class MagazineSeriesPublicationDatesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek Fact Files'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private MagazineSeriesPublicationDatesFixedValueProvider magazineSeriesPublicationDatesFixedValueProvider

	void setup() {
		magazineSeriesPublicationDatesFixedValueProvider = new MagazineSeriesPublicationDatesFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.from.month == null
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.from.year == 1997
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.to.month == null
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.to.year == 2008
	}

	void "provides missing range"() {
		expect:
		!magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		magazineSeriesPublicationDatesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
