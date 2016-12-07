package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import spock.lang.Specification

class GenderFixedValueProviderTest extends Specification {

	private GenderFixedValueProvider pageToGenderSupplementaryProcessor

	def setup() {
		pageToGenderSupplementaryProcessor = new GenderFixedValueProvider()
	}

	def "gets fixed value holder when name is found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor.getSearchedValue('Maurishka')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == Gender.F
	}

	def "gets fixed value holder when name is not found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor.getSearchedValue('Brent Spiner')

		then:
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

	def "gets fixed value holder when name is found, but null"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor
				.getSearchedValue('Two Steps From Hell')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == null
	}

}
