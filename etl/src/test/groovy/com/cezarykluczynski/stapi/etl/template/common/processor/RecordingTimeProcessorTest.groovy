package com.cezarykluczynski.stapi.etl.template.common.processor

import spock.lang.Specification
import spock.lang.Unroll

class RecordingTimeProcessorTest extends Specification {

	private RecordingTimeProcessor recordingTimeProcessor

	void setup() {
		recordingTimeProcessor = new RecordingTimeProcessor()
	}

	@Unroll('when #text is passed, #recordingTime in seconds is returned')
	void "parses various text values into recording time in seconds"() {
		expect:
		recordingTime == recordingTimeProcessor.process(text)

		where:
		text         | recordingTime
		null         | null
		''           | null
		'1:02:nope'  | null
		'1:nope:01'  | null
		'nope:02:01' | null
		':22'        | 22
		'46:43'      | 2803
		'116:41'     | 7001
		'2:09:16'    | 7756
		'309:13'     | 18553
		'3:48:41'    | 13721
		'17:18:17'   | 62297
	}

}
