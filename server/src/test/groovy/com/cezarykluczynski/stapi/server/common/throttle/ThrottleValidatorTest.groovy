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

	private RequestCredentialProvider requestCredentialProviderMock

	private FrequentRequestsValidator tooMuchRequestsValidatorMock

	private ThrottleQualifyingService throttleQualifyingServiceMock

	private ThrottleRepository throttleRepositoryMock

	private RequestSpecificThrottleStatistics requestSpecificThrottleStatisticsMock

	private ThrottleValidator throttleValidator

	void setup() {
		requestCredentialProviderMock = Mock()
		tooMuchRequestsValidatorMock = Mock()
		throttleQualifyingServiceMock = Mock()
		throttleRepositoryMock = Mock()
		requestSpecificThrottleStatisticsMock = Mock()
		throttleValidator = new ThrottleValidator(requestCredentialProviderMock, tooMuchRequestsValidatorMock, throttleQualifyingServiceMock,
				throttleRepositoryMock, requestSpecificThrottleStatisticsMock)
	}

	void "when there is too much requests and, returns throttled result"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)
		Message message = Mock()

		when:
		ThrottleResult throttleResult = throttleValidator.validate(message)

		then:
		1 * requestCredentialProviderMock.provideRequestCredential(message) >> requestCredential
		1 * tooMuchRequestsValidatorMock.validate(requestCredential) >> ThrottleResult.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS
		0 * _
		throttleResult.throttle
		throttleResult.throttleReason == ThrottleReason.TOO_SHORT_INTERVAL_BETWEEN_REQUESTS
	}

	void "when there is not too much requests and request is not qualified for throttle, returns not throttled result"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)
		Message message = Mock()

		when:
		ThrottleResult throttleResult = throttleValidator.validate(message)

		then:
		1 * requestCredentialProviderMock.provideRequestCredential(message) >> requestCredential
		1 * tooMuchRequestsValidatorMock.validate(requestCredential) >> ThrottleResult.NOT_THROTTLED
		1 * throttleQualifyingServiceMock.isQualifiedForThrottle() >> false
		0 * _
		!throttleResult.throttle
		throttleResult.throttleReason == null
	}

	void "when requests is qualified for throttle, returns throttled result when decrement by IP is not successful"() {
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
		1 * tooMuchRequestsValidatorMock.validate(requestCredential) >> ThrottleResult.NOT_THROTTLED
		1 * throttleQualifyingServiceMock.isQualifiedForThrottle() >> true
		1 * throttleRepositoryMock.decrementByIpAndGetStatistics(IP_ADDRESS) >> throttleStatistics
		1 * requestSpecificThrottleStatisticsMock.setThrottleStatistics(throttleStatistics)
		1 * throttleStatistics.decremented >> false
		0 * _
		throttleResult.throttle
		throttleResult.throttleReason == ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED
	}

	void "when requests is qualified for throttle, returns not throttled result when decrement by IP is successful"() {
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
		1 * tooMuchRequestsValidatorMock.validate(requestCredential) >> ThrottleResult.NOT_THROTTLED
		1 * throttleQualifyingServiceMock.isQualifiedForThrottle() >> true
		1 * throttleRepositoryMock.decrementByIpAndGetStatistics(IP_ADDRESS) >> throttleStatistics
		1 * requestSpecificThrottleStatisticsMock.setThrottleStatistics(throttleStatistics)
		1 * throttleStatistics.decremented >> true
		0 * _
		!throttleResult.throttle
		throttleResult.throttleReason == null
	}

}
