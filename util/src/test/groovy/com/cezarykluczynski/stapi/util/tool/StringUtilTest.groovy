package com.cezarykluczynski.stapi.util.tool

import spock.lang.Specification
import spock.lang.Unroll

class StringUtilTest extends Specification {

	@Unroll('tells if #subject start with any of #candidates, ignoring case')
	void "tells if string starts with any of the given strings, ignoring case"() {
		expect:
		result == StringUtil.startsWithAnyIgnoreCase(subject, candidates)

		where:
		subject | candidates     | result
		null    | ['def', 'abc'] | false
		'ABC'   | ['qwerty']     | false
		'abc'   | null           | false
		'abc'   | ['', null]     | false
		'ABC'   | ['DEF', 'ABC'] | true
		'ABC'   | ['def', 'abc'] | true
		'abc'   | ['def', 'abc'] | true
		'abc'   | ['DEF', 'ABC'] | true
	}

	@Unroll('tells if #subject contains any of #candidates, ignoring case')
	void "tells if string contains any of the given strings, ignoring case"() {
		expect:
		result == StringUtil.containsAnyIgnoreCase(subject, candidates)

		where:
		subject    | candidates     | result
		null       | ['def', 'abc'] | false
		'blah ABC' | ['qwerty']     | false
		'blah abc' | null           | false
		'blah abc' | ['', null]     | false
		'blah ABC' | ['DEF', 'ABC'] | true
		'blah ABC' | ['def', 'abc'] | true
		'blah abc' | ['def', 'abc'] | true
		'blah abc' | ['DEF', 'ABC'] | true
	}

}
