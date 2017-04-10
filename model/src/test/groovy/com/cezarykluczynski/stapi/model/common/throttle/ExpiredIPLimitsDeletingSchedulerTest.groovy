package com.cezarykluczynski.stapi.model.common.throttle

import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import spock.lang.Specification

class ExpiredIPLimitsDeletingSchedulerTest extends Specification {

	private ThrottleRepository throttleRepositoryMock

	private ExpiredIPLimitsDeletingScheduler expiredIPLimitsDeletingScheduler

	void setup() {
		throttleRepositoryMock = Mock()
		expiredIPLimitsDeletingScheduler = new ExpiredIPLimitsDeletingScheduler(throttleRepositoryMock)
	}

	void "when called, calls repository"() {
		when:
		expiredIPLimitsDeletingScheduler.delete()

		then:
		1 * throttleRepositoryMock.deleteExpiredIPLimits()
		0 * _
	}

}
