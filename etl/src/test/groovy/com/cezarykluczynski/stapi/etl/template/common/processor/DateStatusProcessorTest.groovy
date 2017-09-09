package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector
import spock.lang.Specification

class DateStatusProcessorTest extends Specification {

	private static final String ITEM = 'ITEM'

	private PeriodCandidateDetector periodCandidateDetectorMock

	private DateStatusProcessor dateStatusProcessor

	void setup() {
		periodCandidateDetectorMock = Mock()
		dateStatusProcessor = new DateStatusProcessor(periodCandidateDetectorMock)
	}

	void "returns original value when PeriodCandidateDetector returns true"() {
		when:
		String result = dateStatusProcessor.process(ITEM)

		then:
		1 * periodCandidateDetectorMock.isPeriodCandidate(ITEM) >> true
		0 * _
		result == ITEM
	}

	void "returns null when PeriodCandidateDetector returns false"() {
		when:
		String result = dateStatusProcessor.process(ITEM)

		then:
		1 * periodCandidateDetectorMock.isPeriodCandidate(ITEM) >> false
		0 * _
		result == null
	}

}
