package com.cezarykluczynski.stapi.util.tool

import spock.lang.Specification

class NumberUtilTest extends Specification {

	def "throws exception when min is larger than max"() {
		when:
		NumberUtil.ensureWithinRangeInclusive(7, 5, 3)

		then:
		IllegalArgumentException illegalArgumentException = thrown(IllegalArgumentException)
		illegalArgumentException.message == 'Min value 7 cannot is larger than max value 3'
	}

	def "tolerates all the same values"() {
		when:
		Integer result = NumberUtil.ensureWithinRangeInclusive(3, 3, 3)

		then:
		result == 3
	}

	def "corrects when values is smaller than min"() {
		when:
		Integer result = NumberUtil.ensureWithinRangeInclusive(3, 2, 7)

		then:
		result == 3
	}

	def "corrects when values is larger than max"() {
		when:
		Integer result = NumberUtil.ensureWithinRangeInclusive(3, 8, 7)

		then:
		result == 7
	}

	def "returns original value when it is within range"() {
		when:
		Integer result = NumberUtil.ensureWithinRangeInclusive(3, 5, 7)

		then:
		result == 5
	}

}
