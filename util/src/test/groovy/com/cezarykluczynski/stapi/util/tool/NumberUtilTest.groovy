package com.cezarykluczynski.stapi.util.tool

import spock.lang.Specification
import spock.lang.Unroll

class NumberUtilTest extends Specification {

	void "throws exception when min is larger than max"() {
		when:
		NumberUtil.ensureWithinRangeInclusive(5, 7, 3)

		then:
		IllegalArgumentException illegalArgumentException = thrown(IllegalArgumentException)
		illegalArgumentException.message == 'Min value 7 cannot is larger than max value 3'
	}

	@Unroll('when #subject should be in range #min-#max, #result is returned')
	void "ensures number if within range inclusive"() {
		expect:
		result == NumberUtil.ensureWithinRangeInclusive(subject, min, max)

		where:
		subject | min | max | result
		3       | 3   | 3   | 3
		2       | 3   | 7   | 3
		8       | 3   | 7   | 7
		5       | 3   | 7   | 5
	}

	@Unroll('returns #result telling if #subject is in range #min-#max')
	void "tells if number if within range inclusive"() {
		expect:
		result == NumberUtil.inRangeInclusive(subject, min, max)

		where:
		subject | min | max | result
		3       | 3   | 3   | true
		2       | 3   | 7   | false
		8       | 3   | 7   | false
		5       | 3   | 7   | true
	}

}
