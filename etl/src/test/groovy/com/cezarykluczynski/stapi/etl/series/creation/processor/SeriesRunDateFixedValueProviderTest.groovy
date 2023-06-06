package com.cezarykluczynski.stapi.etl.series.creation.processor

import spock.lang.Specification

import java.time.LocalDate

class SeriesRunDateFixedValueProviderTest extends Specification {

	private static final String EXISTING_ABBREVIATION = 'AT'
	private static final String NONEXISTING_ABBREVIATION = 'NONEXISTING_ABBREVIATION'

	private SeriesRunDateFixedValueProvider seriesRunDateFixedValueProvider

	void setup() {
		seriesRunDateFixedValueProvider = new SeriesRunDateFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		seriesRunDateFixedValueProvider.getSearchedValue(EXISTING_ABBREVIATION).found
		seriesRunDateFixedValueProvider.getSearchedValue(EXISTING_ABBREVIATION).value.startDate == LocalDate.of(2017, 9, 24)
		seriesRunDateFixedValueProvider.getSearchedValue(EXISTING_ABBREVIATION).value.endDate == LocalDate.of(2018, 2, 11)
	}

	void "provides missing range"() {
		expect:
		!seriesRunDateFixedValueProvider.getSearchedValue(NONEXISTING_ABBREVIATION).found
		seriesRunDateFixedValueProvider.getSearchedValue(NONEXISTING_ABBREVIATION).value == null
	}

}
