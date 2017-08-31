package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector
import spock.lang.Specification

class StarshipDateStatusProcessorTest extends Specification {

	private static final String ITEM = 'ITEM'

	private PeriodCandidateDetector periodCandidateDetectorMock

	private StarshipDateStatusProcessor starshipDateStatusProcessor

	void setup() {
		periodCandidateDetectorMock = Mock()
		starshipDateStatusProcessor = new StarshipDateStatusProcessor(periodCandidateDetectorMock)
	}

	void "returns original value when PeriodCandidateDetector returns true"() {
		when:
		String result = starshipDateStatusProcessor.process(ITEM)

		then:
		1 * periodCandidateDetectorMock.isPeriodCandidate(ITEM) >> true
		0 * _
		result == ITEM
	}

	void "returns null when PeriodCandidateDetector returns false"() {
		when:
		String result = starshipDateStatusProcessor.process(ITEM)

		then:
		1 * periodCandidateDetectorMock.isPeriodCandidate(ITEM) >> false
		0 * _
		result == null
	}

}
