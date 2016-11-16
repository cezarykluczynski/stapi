package com.cezarykluczynski.stapi.etl.template.individual.processor

import spock.lang.Specification
import spock.lang.Unroll

class IndividualWeightProcessorTest extends Specification {

	private IndividualWeightProcessor individualWeightProcessor

	def setup() {
		individualWeightProcessor = new IndividualWeightProcessor()
	}

	@Unroll("#output is returned when #input is passed")
	def "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualWeightProcessor.process(input) == output

		where:
		input            | output
		"116 lb."        | 53
		"120 [[pound]]s" | 54
		"240 lbs"        | 109
		""               | null
		null             | null
	}

}
