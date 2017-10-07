package com.cezarykluczynski.stapi.server.common.throttle

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties
import com.google.common.cache.Cache
import spock.lang.Specification

import java.time.LocalDateTime

class FrequentRequestsCacheBuilderTest extends Specification {

	private ThrottleProperties throttlePropertiesMock

	private FrequentRequestsCacheBuilder frequentRequestsCacheBuilder

	void setup() {
		throttlePropertiesMock = Mock()
		frequentRequestsCacheBuilder = new FrequentRequestsCacheBuilder(throttlePropertiesMock)
	}

	void "build cache once"() {
		when:
		Cache<FrequentRequestsKey, List<LocalDateTime>> cache = frequentRequestsCacheBuilder.build()

		then:
		1 * throttlePropertiesMock.frequentRequestsPeriodLengthInSeconds >> 15
		0 * _
		cache != null

		when:
		frequentRequestsCacheBuilder.build()

		then:
		thrown(IllegalStateException)
	}

}
