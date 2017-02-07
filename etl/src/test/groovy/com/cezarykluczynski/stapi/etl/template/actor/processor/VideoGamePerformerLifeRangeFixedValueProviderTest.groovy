package com.cezarykluczynski.stapi.etl.template.actor.processor

import spock.lang.Specification

import java.time.LocalDate

class VideoGamePerformerLifeRangeFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Robert V. Barron'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private VideoGamePerformerLifeRangeFixedValueProvider videoGamePerformerLifeRangeFixedValueProvider

	void setup() {
		videoGamePerformerLifeRangeFixedValueProvider = new VideoGamePerformerLifeRangeFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.dateOfBirth == LocalDate.of(1932, 12, 26)
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.placeOfBirth == 'Charleston, West Virginia, USA'
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.dateOfDeath == LocalDate.of(2000, 12, 1)
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.placeOfDeath == 'Salinas, California, USA'
	}

	void "provides missing range"() {
		expect:
		!videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		videoGamePerformerLifeRangeFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
