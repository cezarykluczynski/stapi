package com.cezarykluczynski.stapi.etl.template.individual.processor.species

import org.apache.commons.lang3.math.Fraction
import spock.lang.Specification

class CharacterSpeciesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Nakamura'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private CharacterSpeciesFixedValueProvider characterSpeciesFixedValueProvider

	void setup() {
		characterSpeciesFixedValueProvider = new CharacterSpeciesFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		characterSpeciesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		characterSpeciesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.get('Human') == Fraction.getFraction(1, 1)
	}

	void "provides missing range"() {
		expect:
		!characterSpeciesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		characterSpeciesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
