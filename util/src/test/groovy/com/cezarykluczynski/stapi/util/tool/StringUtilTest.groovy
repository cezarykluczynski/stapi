package com.cezarykluczynski.stapi.util.tool

import com.google.common.collect.Lists
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

	@Unroll('returns #positions when asked for positions of #search in #subject')
	void "returns all positions of a substring in a string"() {
		expect:
		result == StringUtil.getAllSubstringPositions(subject, search)

		where:
		subject                                        | search     | result
		''                                             | 'a'        | Lists.newArrayList()
		'a'                                            | null       | Lists.newArrayList()
		'a'                                            | ''         | Lists.newArrayList()
		'bac'                                          | 'a'        | Lists.newArrayList(1)
		'bacad'                                        | 'a'        | Lists.newArrayList(1, 3)
		'aaaaa'                                        | 'a'        | Lists.newArrayList(0, 1, 2, 3, 4)
		'aabbaabbaa'                                   | 'bb'       | Lists.newArrayList(2, 6)
		'&frac12; [[Human]]<br />&frac12; [[Ocampa]]n' | '&frac12;' | Lists.newArrayList(0, 24)
	}

}
