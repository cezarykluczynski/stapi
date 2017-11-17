package com.cezarykluczynski.stapi.model.common.throttle

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import spock.lang.Specification

class ThrottleApiKeyLimitsRegeneratingSchedulerTest extends Specification {

	private ThrottleRepository throttleRepositoryMock

	private ThrottleApiKeyLimitsRegeneratingScheduler throttleApiKeyLimitsRegeneratingScheduler

	void setup() {
		throttleRepositoryMock = Mock()
		throttleApiKeyLimitsRegeneratingScheduler = new ThrottleApiKeyLimitsRegeneratingScheduler(throttleRepositoryMock)
	}

	void "when called, calls repository"() {
		when:
		throttleApiKeyLimitsRegeneratingScheduler.regenerate()

		then:
		1 * throttleRepositoryMock.regenerateApiKeysRemainingHits()
		0 * _
	}

}
