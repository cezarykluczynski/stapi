package com.cezarykluczynski.stapi.model.common.throttle

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import spock.lang.Specification

class ThrottleIPLimitsRegeneratingSchedulerTest extends Specification {

	private ThrottleRepository throttleRepositoryMock

	private ThrottleIPLimitsRegeneratingScheduler throttleIPLimitsRegeneratingScheduler

	void setup() {
		throttleRepositoryMock = Mock()
		throttleIPLimitsRegeneratingScheduler = new ThrottleIPLimitsRegeneratingScheduler(throttleRepositoryMock)
	}

	void "when called, calls repository"() {
		when:
		throttleIPLimitsRegeneratingScheduler.regenerate()

		then:
		1 * throttleRepositoryMock.regenerateIPAddressesRemainingHits()
		0 * _
	}

}
