package com.cezarykluczynski.stapi.etl.location.creation.processor

import spock.lang.Specification

class LocationNameFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Fusion (night club)'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private LocationNameFixedValueProvider locationNameFixedValueProvider

	void setup() {
		locationNameFixedValueProvider = new LocationNameFixedValueProvider()
	}

	void "provides correct Location name"() {
		expect:
		locationNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		locationNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'Fusion'
	}

	void "provides missing Location name"() {
		expect:
		!locationNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		locationNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
