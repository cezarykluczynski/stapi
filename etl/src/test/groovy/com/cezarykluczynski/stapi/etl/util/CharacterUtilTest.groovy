package com.cezarykluczynski.stapi.etl.util

import spock.lang.Specification
import spock.lang.Unroll

class CharacterUtilTest extends Specification {

	@Unroll("when '#phrase' is passed, #result is obtained")
	void "detects one of many"() {
		expect:
		CharacterUtil.isOneOfMany(phrase) == result

		where:
		phrase                    | result
		'Klingon 23rd patrol 001' | true
		'Klingon 23rd patrol 002' | true
		'Q 001'                   | true
		' 002'                    | false
		'001'                     | false
		'002'                     | false
		'Jean-Luc Picard'         | false
		''                        | false
		null                      | false

	}

}
