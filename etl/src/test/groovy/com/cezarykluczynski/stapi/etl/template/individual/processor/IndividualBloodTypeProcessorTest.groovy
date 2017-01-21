package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import spock.lang.Specification
import spock.lang.Unroll

class IndividualBloodTypeProcessorTest extends Specification {

	private IndividualBloodTypeProcessor individualBloodTypeProcessor

	void setup() {
		individualBloodTypeProcessor = new IndividualBloodTypeProcessor()
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualBloodTypeProcessor.process(input) == output

		where:
		input                                   | output
		IndividualBloodTypeProcessor.B_NEGATIVE | BloodType.B_NEGATIVE
		IndividualBloodTypeProcessor.O_NEGATIVE | BloodType.O_NEGATIVE
		IndividualBloodTypeProcessor.T_NEGATIVE | BloodType.T_NEGATIVE
		''                                      | null
		null                                    | null
	}

}
