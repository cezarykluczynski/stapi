package com.cezarykluczynski.stapi.etl.template.individual.processor

import spock.lang.Specification
import spock.lang.Unroll

class IndividualHeightProcessorTest extends Specification {

	private IndividualHeightProcessor individualHeightProcessor

	void setup() {
		individualHeightProcessor = new IndividualHeightProcessor()
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualHeightProcessor.process(input) == output

		where:
		input                     | output
		"5' 7\""                  | 170
		'182 cm (as of [[2377]])' | 182
		"5'2\""                   | 157
		'Approx. 4 ft.'           | 122
		"6'4\" (193 cm)"          | 193
		''                        | null
		null                      | null
	}

}
