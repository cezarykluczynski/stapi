package com.cezarykluczynski.stapi.etl.template.individual.processor

import spock.lang.Specification

class IndividualTemplatePlacesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Moses'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProvider

	void setup() {
		individualTemplatePlacesFixedValueProvider = new IndividualTemplatePlacesFixedValueProvider()
	}

	void "provides correct places of birth and death"() {
		expect:
		individualTemplatePlacesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		individualTemplatePlacesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.placeOfBirth == 'Earth'
		individualTemplatePlacesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.placeOfDeath == 'Earth'
	}

	void "provides missing places of birth and death"() {
		expect:
		!individualTemplatePlacesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		individualTemplatePlacesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
