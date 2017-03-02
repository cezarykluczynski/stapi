package com.cezarykluczynski.stapi.etl.species.creation.processor

import com.cezarykluczynski.stapi.etl.template.species.processor.SpeciesTypeFixedValueProvider
import spock.lang.Specification

class SpeciesTypeFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Ferengi'
	private static final String NONEXISTING_TITLE = 'Mintakan'

	private SpeciesTypeFixedValueProvider speciesTypeFixedValueProvider

	void setup() {
		speciesTypeFixedValueProvider = new SpeciesTypeFixedValueProvider()
	}

	void "provides correct value"() {
		expect:
		speciesTypeFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		speciesTypeFixedValueProvider.getSearchedValue(EXISTING_TITLE).value
	}

	void "provides missing value"() {
		expect:
		!speciesTypeFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		speciesTypeFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
