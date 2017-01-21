package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import spock.lang.Specification

class GenderFixedValueProviderTest extends Specification {

	private GenderFixedValueProvider pageToGenderSupplementaryProcessor

	void setup() {
		pageToGenderSupplementaryProcessor = new GenderFixedValueProvider()
	}

	void "gets fixed value holder when name is found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor.getSearchedValue('Maurishka')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == Gender.F
	}

	void "gets fixed value holder when name is not found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor.getSearchedValue('Brent Spiner')

		then:
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

	void "gets fixed value holder when name is found, but null"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = pageToGenderSupplementaryProcessor
				.getSearchedValue('Two Steps From Hell')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == null
	}

}
