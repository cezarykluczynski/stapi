package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import spock.lang.Specification
import spock.lang.Unroll

class NumberOfPartsProcessorTest extends Specification {

	private NumberOfPartsProcessor numberOfPartsProcessor

	void setup() {
		numberOfPartsProcessor = new NumberOfPartsProcessor()
	}

	@Unroll('when #input is given, #output is returned')
	void "number of parts is extracted"() {
		given:
		expect:
		output == numberOfPartsProcessor.process(input)

		where:
		input    | output
		null     | null
		''       | null
		'String' | null
		'1'      | 1
		'200'    | 200
		'75+'    | 75
		'1,000'  | 1000
		'75++'   | null
		'75-'    | null
	}

}
