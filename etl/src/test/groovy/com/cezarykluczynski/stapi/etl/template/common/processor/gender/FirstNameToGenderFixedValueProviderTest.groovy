package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import spock.lang.Specification

class FirstNameToGenderFixedValueProviderTest extends Specification {

    private FirstNameToGenderFixedValueProvider firstNameToGenderFixedValueProvider

    void setup() {
        firstNameToGenderFixedValueProvider = new FirstNameToGenderFixedValueProvider()
    }

    void "gets fixed value holder when name is found"() {
        when:
        FixedValueHolder<Gender> fixedValueHolder = firstNameToGenderFixedValueProvider.getSearchedValue('John')

        then:
        fixedValueHolder.found
        fixedValueHolder.value == Gender.M
    }

    void "gets fixed value holder when name is not found"() {
        when:
        FixedValueHolder<Gender> fixedValueHolder = firstNameToGenderFixedValueProvider.getSearchedValue('Aggel')

        then:
        !fixedValueHolder.found
        fixedValueHolder.value == null
    }

}
