package com.cezarykluczynski.stapi.etl.template.common.processor

import spock.lang.Specification
import spock.lang.Unroll

class RunTimeProcessorTest extends Specification {

	private RunTimeProcessor runTimeProcessor

	void setup() {
		runTimeProcessor = new RunTimeProcessor()
	}

	@Unroll('when #text is passed, #runTime minutes is returned')
	void "parses various text values into run time in minutes"() {
		expect:
		runTime == runTimeProcessor.process(text)

		where:
		text                                                                            | runTime
		''                                                                              | null
		'3 hours'                                                                       | 180
		'4 hours, 30 minutes'                                                           | 270
		'93 minutes'                                                                    | 93
		'3 hours (abridged)<br />5 hours, 47 minutes (unabridged)'                      | 347
		'90 Minutes<br />(1 Cassette)'                                                  | 90
		'3 hours (2 cassettes)<br />6 hours, 31 minutes (Audible)'                      | 391
		'3 hours (2 cassettes)'                                                         | 180
		'180 Minutes<br />(on 2 cassettes)'                                             | 180
		'3 hours (abridged)'                                                            | 180
		'1 hour, 30 minutes'                                                            | 90
		'4 hours (abridged)<br />8 hours, 30 minutes (unabridged)'                      | 510
		'90 minutes<br />(on 1 Cassette)'                                               | 90
		'10 hours, 30 minutes<br>3 hours, 44 minutes (abridged)'                        | 630
		'Approx. 16 Minutes'                                                            | 16
		'Approximately 20 Minutes'                                                      | 20
		'116 minutes (NTSC)<br/>112 minutes (PAL)'                                      | 116
		'939 minutes (region 1)<br />897 minutes (region 2)<br />902 minutes (Germany)' | 939
		'129 hours and 42 minutes'                                                      | 7782
		'1,181 minutes'                                                                 | 1181
		'15:07'                                                                         | 907
	}

}
