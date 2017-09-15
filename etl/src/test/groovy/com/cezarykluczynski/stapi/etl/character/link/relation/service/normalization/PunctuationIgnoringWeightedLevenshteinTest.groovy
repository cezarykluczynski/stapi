package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization

import spock.lang.Specification
import spock.lang.Unroll

class PunctuationIgnoringWeightedLevenshteinTest extends Specification {

	private PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein

	void setup() {
		punctuationIgnoringWeightedLevenshtein = new PunctuationIgnoringWeightedLevenshtein()
	}

	@Unroll('when "#left" and "#right" is passed, #distance distance is returned')
	void "ignores punctuation and white characters"() {
		expect:
		punctuationIgnoringWeightedLevenshtein.distance(left, right) == distance

		where:
		left                         | right                        | distance
		'a'                          | 'a'                          | 0
		'paternal great-grandfather' | 'paternal-great-grandfather' | 0
		'one,two,three'              | 'one two three'              | 0
		'great,great grandmother'    | 'great-great-grandmother'    | 0
		'one-two-three'              | 'one two tree'               | 1
		'1 2 3'                      | '1,2,3,4'                    | 2
		'Hello, world'               | 'Helloworld'                 | 2
	}

}
