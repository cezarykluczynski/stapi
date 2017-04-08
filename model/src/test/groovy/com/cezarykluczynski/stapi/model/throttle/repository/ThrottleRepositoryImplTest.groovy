package com.cezarykluczynski.stapi.model.throttle.repository

import com.cezarykluczynski.stapi.model.configuration.ThrottleProperties
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class ThrottleRepositoryImplTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'
	private static final Integer IP_ADDRESS_HOURLY_LIMIT = 100

	private ThrottleProperties throttlePropertiesMock

	private ThrottleRepository throttleRepositoryMock

	private ThrottleRepositoryImpl throttleRepositoryImpl

	void setup() {
		throttlePropertiesMock = Mock()
		throttleRepositoryMock = Mock()
		throttleRepositoryImpl = new ThrottleRepositoryImpl(throttlePropertiesMock)
		throttleRepositoryImpl.throttleRepository = throttleRepositoryMock
	}

	void "decrements existing IP and returns true when it was successful"() {
		given:
		Throttle throttle = new Throttle(remainingHits: 10)

		when:
		boolean result = throttleRepositoryImpl.decrementByIpAndGetResult(IP_ADDRESS)

		then:
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		1 * throttleRepositoryMock.decrementByIp(IP_ADDRESS, _)
		0 * _
		result
	}

	void "decrements new IP and returns true"() {
		when:
		boolean result = throttleRepositoryImpl.decrementByIpAndGetResult(IP_ADDRESS)

		then:
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.empty()
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.save(_ as Throttle) >> { Throttle throttle ->
			assert throttle.throttleType == ThrottleType.IP_ADDRESS
			assert throttle.ipAddress == IP_ADDRESS
			assert throttle.hitsLimit == IP_ADDRESS_HOURLY_LIMIT
			assert throttle.remainingHits == IP_ADDRESS_HOURLY_LIMIT - 1
			assert throttle.lastHitTime != null
		}
		0 * _
		result
	}

	void "decrements existing IP when save throws DataIntegrityViolationException, then returns true"() {
		when:
		boolean result = throttleRepositoryImpl.decrementByIpAndGetResult(IP_ADDRESS)

		then:
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.empty()
		1 * throttlePropertiesMock.ipAddressHourlyLimit >> IP_ADDRESS_HOURLY_LIMIT
		1 * throttleRepositoryMock.save(_ as Throttle) >> { Throttle throttle ->
			throw new DataIntegrityViolationException('')
		}
		1 * throttleRepositoryMock.decrementByIp(IP_ADDRESS, _)
		0 * _
		result
	}

	void "returns false when there are 0 remaining hits"() {
		given:
		Throttle throttle = new Throttle(remainingHits: 0)

		when:
		boolean result = throttleRepositoryImpl.decrementByIpAndGetResult(IP_ADDRESS)

		then:
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		0 * _
		!result
	}

	void "returns false when there are less than 0 remaining hits"() {
		given:
		Throttle throttle = new Throttle(remainingHits: -1)

		when:
		boolean result = throttleRepositoryImpl.decrementByIpAndGetResult(IP_ADDRESS)

		then:
		1 * throttleRepositoryMock.findByIpAddress(IP_ADDRESS) >> Optional.of(throttle)
		0 * _
		!result
	}

}
