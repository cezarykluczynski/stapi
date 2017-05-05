package com.cezarykluczynski.stapi.server.common.throttle

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredential
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialProvider
import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType
import org.apache.cxf.message.Message
import spock.lang.Specification

class ThrottleValidatorTest extends Specification {

	private static final String IP_ADDRESS = 'IP_ADDRESS'

	private ThrottleRepository throttleRepositoryMock

	private RequestCredentialProvider requestCredentialProviderMock

	private RequestSpecificThrottleStatistics requestSpecificThrottleStatisticsMock

	private ThrottleValidator throttleValidator

	void setup() {
		throttleRepositoryMock = Mock()
		requestCredentialProviderMock = Mock()
		requestSpecificThrottleStatisticsMock = Mock()
		throttleValidator = new ThrottleValidator(throttleRepositoryMock, requestCredentialProviderMock, requestSpecificThrottleStatisticsMock)
	}

	void "return throttled result when decrement by IP is not successful"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)
		ThrottleStatistics throttleStatistics = Mock()
		Message message = Mock()

		when:
		ThrottleResult throttleResult = throttleValidator.validate(message)

		then:
		1 * requestCredentialProviderMock.provideRequestCredential(message) >> requestCredential
		1 * throttleRepositoryMock.decrementByIpAndGetStatistics(IP_ADDRESS) >> throttleStatistics
		1 * requestSpecificThrottleStatisticsMock.setThrottleStatistics(throttleStatistics)
		1 * throttleStatistics.decremented >> false
		0 * _
		throttleResult.throttle
		throttleResult.throttleReason == ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED
	}

	void "return not throttled result when decrement by IP is successful"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)
		ThrottleStatistics throttleStatistics = Mock()
		Message message = Mock()

		when:
		ThrottleResult throttleResult = throttleValidator.validate(message)

		then:
		1 * requestCredentialProviderMock.provideRequestCredential(message) >> requestCredential
		1 * throttleRepositoryMock.decrementByIpAndGetStatistics(IP_ADDRESS) >> throttleStatistics
		1 * requestSpecificThrottleStatisticsMock.setThrottleStatistics(throttleStatistics)
		1 * throttleStatistics.decremented >> true
		0 * _
		!throttleResult.throttle
		throttleResult.throttleReason == null
	}

}
