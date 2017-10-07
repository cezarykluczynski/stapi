package com.cezarykluczynski.stapi.server.common.service

import spock.lang.Specification

import java.time.LocalDateTime

class NowProviderTest extends Specification {

	private NowProvider nowProvider

	void setup() {
		nowProvider = new NowProvider()
	}

	void "gets current LocalDateTime"() {
		given:
		LocalDateTime before = LocalDateTime.now()

		when:
		LocalDateTime now = nowProvider.now()

		then:
		before.isBefore(now) || before.isEqual(now)

		when:
		LocalDateTime after = LocalDateTime.now()

		then:
		after.isEqual(now) || after.isAfter(now)
	}

}
