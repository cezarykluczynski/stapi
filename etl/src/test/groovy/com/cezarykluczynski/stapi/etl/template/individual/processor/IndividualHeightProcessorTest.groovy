package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class IndividualHeightProcessorTest extends Specification {

	private IndividualHeightProcessor individualHeightProcessor

	void setup() {
		individualHeightProcessor = new IndividualHeightProcessor(new WikitextApiImpl())
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualHeightProcessor.process(input) == output

		where:
		input                                        | output
		"5' 7\""                                     | 170
		'182 cm (as of [[2377]])'                    | 182
		'175.26 [[cm]]'                              | 175
		'1.75 [[meter]]s'                            | 175
		'1.73m'                                      | 173
		"5'2\""                                      | 157
		'Approx. 4 ft.'                              | 122
		'Approx. 4 [[feet|ft.]]'                     | 122
		"6'4\" (193 cm)"                             | 193
		'6[[foot|\']]2[[inch|"]]'                    | 188
		'182 [[centimeter|cm]]'                      | 182
		'6\''                                        | 183
		'139.7 [[centimeter|cm]]'                    | 140
		'1.8 [[meter|m]]'                            | 180
		'6[[foot|\']]5[[inch|"]]'                    | 196
		'5\' 8.5"'                                   | 174
		'{"content":"6 [[foot|feet]]"}'              | 183
		'{"content":"6[[foot|\']]"}'                 | 183
		'{"content":"5[[foot|\']]7[[inch|\\"]]"}'    | 170
		'{"content":"191 [[cm]]"}'                   | 191
		'{"content":"5\'8\\""}'                      | 173
		'{"content":"6\""}'                          | 15
		'{"content":"1.84 [[meter]]s"}'              | 184
		'{"content":"5[[foot|\']]11.5[[inch|\\"]]"}' | 182
		'50'                                         | null
		''                                           | null
		null                                         | null
	}

}
