package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import spock.lang.Specification

class RealWorldPersonGenderFixedValueProviderTest extends Specification {

	private RealWorldPersonGenderFixedValueProvider realWorldPersonGenderFixedValueProvider

	void setup() {
		realWorldPersonGenderFixedValueProvider = new RealWorldPersonGenderFixedValueProvider()
	}

	void "gets fixed value holder when name is found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = realWorldPersonGenderFixedValueProvider.getSearchedValue('Maurishka')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == Gender.F
	}

	void "gets fixed value holder when name is not found"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = realWorldPersonGenderFixedValueProvider.getSearchedValue('Brent Spiner')

		then:
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

	void "gets fixed value holder when name is found, but null"() {
		when:
		FixedValueHolder<Gender> fixedValueHolder = realWorldPersonGenderFixedValueProvider
				.getSearchedValue('Two Steps From Hell')

		then:
		fixedValueHolder.found
		fixedValueHolder.value == null
	}

}
