package com.cezarykluczynski.stapi.etl.template.individual.processor

import spock.lang.Specification
import spock.lang.Unroll

class IndividualWeightProcessorTest extends Specification {

	private IndividualWeightProcessor individualWeightProcessor

	void setup() {
		individualWeightProcessor = new IndividualWeightProcessor()
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualWeightProcessor.process(input) == output

		where:
		input            | output
		'116 lb.'        | 53
		'116 pounds'     | 53
		'120 [[pound]]s' | 54
		'240 lbs'        | 109
		'77 kg'          | 77
		''               | null
		null             | null
	}

}
