package com.cezarykluczynski.stapi.etl.season.creation.processor

import spock.lang.Specification

class SeasonNumberOfEpisodesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'The Ready Room Season 1'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private SeasonNumberOfEpisodesFixedValueProvider seasonNumberOfEpisodesFixedValueProvider

	void setup() {
		seasonNumberOfEpisodesFixedValueProvider = new SeasonNumberOfEpisodesFixedValueProvider()
	}

	void "provides correct value"() {
		expect:
		seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 13
	}

	void "provides missing value"() {
		expect:
		!seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		seasonNumberOfEpisodesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
