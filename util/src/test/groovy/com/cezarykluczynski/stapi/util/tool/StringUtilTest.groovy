package com.cezarykluczynski.stapi.util.tool

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification
import spock.lang.Unroll

class StringUtilTest extends Specification {

	@Unroll('tells if #subject start with any of #candidates, ignoring case')
	void "tells if string starts with any of the given strings, ignoring case"() {
		expect:
		StringUtil.startsWithAnyIgnoreCase(subject, candidates) == result

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
		StringUtil.containsAnyIgnoreCase(subject, candidates) == result

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

	@Unroll('tells if #subject contains all of #candidates, ignoring case')
	void "tells if string contains all of the given strings, ignoring case"() {
		expect:
		StringUtil.containsAllIgnoreCase(subject, candidates) == result

		where:
		subject    | candidates     | result
		null       | ['def', 'abc'] | false
		'blah ABC' | ['qwerty']     | false
		'blah abc' | null           | false
		'blah abc' | ['', null]     | false
		'blah ABC' | ['DEF', 'ABC'] | false
		'efg ABC'  | ['efg', 'abc'] | true
		'DEF abc'  | ['def', 'abc'] | true
		'abc'      | ['ABC']        | true
	}

	@Unroll('returns #positions when asked for positions of #search in #subject')
	void "returns all positions of a substring in a string"() {
		expect:
		StringUtil.getAllSubstringPositions(subject, search) == result

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

	@Unroll('returns #result if any of #stringList starts with #suffix ignore case')
	void "tells when any string starts with given suffix"() {
		expect:
		StringUtil.anyStartsWithIgnoreCase(stringList, suffix) == result

		where:
		stringList                              | suffix       | result
		Lists.newArrayList()                    | ''           | false
		Lists.newArrayList('Government agency') | 'agency'     | false
		Lists.newArrayList('Government agency') | 'Agency'     | false
		Lists.newArrayList('Government agency') | 'government' | true
		Lists.newArrayList('Government agency') | 'Government' | true
	}

	@Unroll('returns #result if any of #stringList ends with #suffix ignore case')
	void "tells when any string ends with given suffix"() {
		expect:
		result == StringUtil.anyEndsWithIgnoreCase(stringList, suffix)

		where:
		stringList                              | suffix       | result
		Lists.newArrayList()                    | ''           | false
		Lists.newArrayList('Government agency') | 'agency'     | true
		Lists.newArrayList('Government agency') | 'Agency'     | true
		Lists.newArrayList('Government agency') | 'government' | false
		Lists.newArrayList('Government agency') | 'Government' | false
	}

	@Unroll('returns #result when #subject ends with any of #suffixList')
	void "tells when a string any with any of a given string from the list"() {
		expect:
		StringUtil.endsWithAny(subject, suffixList) == result

		where:
		subject   | suffixList                           | result
		'test'    | Lists.newArrayList('est')            | true
		'last'    | Lists.newArrayList('rd', 'nd', 'st') | true
		'TEST'    | Lists.newArrayList('test')           | false
		'nope'    | Lists.newArrayList('yes', 'yep')     | false
		'subject' | Lists.newArrayList('')               | false
	}

	@Unroll('returns #result when #subject is passed with #suffixed')
	void "returns string cut before any of the given suffixes"() {
		expect:
		StringUtil.substringBeforeAny(subject, suffixList) == result

		where:
		subject                          | suffixList                                         | result
		'AstronomicalObjectBaseResponse' | Lists.newArrayList('BaseResponse', 'FullResponse') | 'AstronomicalObject'
		'BookBase'                       | Lists.newArrayList('Full', 'Base')                 | 'Book'
		'ComicsFullRequest'              | Lists.newArrayList('FullRequest', 'BaseRequest')   | 'Comics'
	}

	@Unroll('returns #result when #stringCollection and #lookup is passed')
	void "returns true or false when collection of string and string to lookup in collection is passed"() {
		expect:
		StringUtil.containsIgnoreCase(stringCollection, lookup) == result

		where:
		stringCollection                           | lookup   | result
		Sets.newHashSet()                          | 'lookup' | false
		Sets.newHashSet('one', 'two')              | 'lookup' | false
		Sets.newHashSet('lookup')                  | 'lookup' | true
		Sets.newHashSet('Lookup')                  | 'Lookup' | true
		Sets.newHashSet('Lookup')                  | 'lookup' | true
		Sets.newHashSet('Lookup')                  | 'Lookup' | true
		Lists.newArrayList('one', 'two', 'lookup') | 'lookup' | true
		Lists.newArrayList('one', 'two', 'lookup') | 'Lookup' | true
	}

}
