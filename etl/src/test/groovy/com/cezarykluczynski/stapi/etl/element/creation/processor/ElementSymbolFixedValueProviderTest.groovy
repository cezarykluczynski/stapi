package com.cezarykluczynski.stapi.etl.element.creation.processor

import spock.lang.Specification

class ElementSymbolFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Hydrogen'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private ElementSymbolFixedValueProvider elementSymbolFixedValueProvider

	void setup() {
		elementSymbolFixedValueProvider = new ElementSymbolFixedValueProvider()
	}

	void "provides correct Element name"() {
		expect:
		elementSymbolFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		elementSymbolFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'H'
	}

	void "provides missing Element name"() {
		expect:
		!elementSymbolFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		elementSymbolFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
