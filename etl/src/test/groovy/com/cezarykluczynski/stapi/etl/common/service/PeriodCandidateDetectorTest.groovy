package com.cezarykluczynski.stapi.etl.common.service

import spock.lang.Specification

class PeriodCandidateDetectorTest extends Specification {

	private PeriodCandidateDetector periodCandidateDetector

	void setup() {
		periodCandidateDetector = new PeriodCandidateDetector()
	}

	void "when string candidate is passed, period is returned"() {
		expect:
		periodCandidateDetector.isPeriodCandidate(input) == output

		where:
		input          | output
		null           | false
		''             | false
		'Unknown'      | false
		'N/A'          | false
		'9'            | false
		'15'           | false
		'2154'         | true
		'2360s'        | true
		'15th century' | true
	}

}
