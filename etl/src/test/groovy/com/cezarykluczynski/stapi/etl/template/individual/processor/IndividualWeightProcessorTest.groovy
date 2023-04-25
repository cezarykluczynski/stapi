package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class IndividualWeightProcessorTest extends Specification {

	private IndividualWeightProcessor individualWeightProcessor

	void setup() {
		individualWeightProcessor = new IndividualWeightProcessor(new WikitextApiImpl())
	}

	@Unroll('#output is returned when #input is passed')
	void "valid input produces not null output, invalid input produces null output"() {
		expect:
		individualWeightProcessor.process(input) == output

		where:
		input                              | output
		'116 lb.'                          | 53
		'116 pounds'                       | 53
		'120 [[pound]]s'                   | 54
		'120 [[Pound]]s'                   | 54
		'168 [[Pound|lbs]]'                | 76
		'240 [[Pound|lbs.]]'               | 109
		'185 [[lbs]]. ([[2280s]])'         | 84
		'240 lbs'                          | 109
		'240 Lbs'                          | 109
		'240 LBS'                          | 109
		'77 kg'                            | 77
		'72 [[kg]]'                        | 72
		'72 [[kilogram]]s'                 | 72
		'54.4 [[Kilogram]]s'               | 54
		'78.5 [[kg]]'                      | 79
		'61.2 kilograms'                   | 61
		'61.7 Kilograms'                   | 62
		'72 [[kilogram]]s'                 | 72
		'{"content":"225 [[pound|lbs.]]"}' | 102
		'{"content":"158 [[pound|lbs]]."}' | 72
		'{"content":"180 [[pound|lb]]s."}' | 82
		'{"content":"52kg"}'               | 52
		'{"content":"145 lbs."}'           | 66
		'{"content":"81.7 [[kilogram]]s"}' | 82
		'50'                               | null
		''                                 | null
		null                               | null
	}

}
