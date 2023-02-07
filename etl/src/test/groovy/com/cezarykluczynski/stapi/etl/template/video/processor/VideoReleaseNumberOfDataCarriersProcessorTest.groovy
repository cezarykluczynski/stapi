package com.cezarykluczynski.stapi.etl.template.video.processor

import spock.lang.Specification
import spock.lang.Unroll

class VideoReleaseNumberOfDataCarriersProcessorTest extends Specification {

	private VideoReleaseNumberOfDataCarriersProcessor videoReleaseNumberOfDataCarriersProcessor

	void setup() {
		videoReleaseNumberOfDataCarriersProcessor = new VideoReleaseNumberOfDataCarriersProcessor()
	}

	@Unroll('when #input is given, #output is returned')
	void "number of parts is extracted"() {
		given:
		expect:
		output == videoReleaseNumberOfDataCarriersProcessor.process(input)

		where:
		input                | output
		null                 | null
		''                   | null
		'String'             | null
		'1'                  | 1
		'2'                  | 2
		'3 (Region 2)'       | 3
		'3 <br /> 4 (Japan)' | 3
		'4+'                 | 4
		'1,000'              | 1000
		'75++'               | null
		'75-'                | null
	}

}
