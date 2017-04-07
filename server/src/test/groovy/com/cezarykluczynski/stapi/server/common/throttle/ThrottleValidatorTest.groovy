package com.cezarykluczynski.stapi.server.common.throttle

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

	private ThrottleValidator throttleValidator

	void setup() {
		throttleRepositoryMock = Mock(ThrottleRepository)
		requestCredentialProviderMock = Mock(RequestCredentialProvider)
		throttleValidator = new ThrottleValidator(throttleRepositoryMock, requestCredentialProviderMock)
	}

	void "return throttled result when decrement by IP is not successful"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)

		when:
		ThrottleResult throttleResult = throttleValidator.validate(Mock(Message))

		then:
		1 * requestCredentialProviderMock.provideRequestCredential() >> requestCredential
		1 * throttleRepositoryMock.decrementByIpAndGetResult(IP_ADDRESS) >> false
		0 * _
		throttleResult.throttle
		throttleResult.throttleReason == ThrottleReason.HOURLY_IP_LIMIT_EXCEEDED
	}

	void "return not throttled result when decrement by IP is successful"() {
		given:
		RequestCredential requestCredential = new RequestCredential(
				requestCredentialType: RequestCredentialType.IP_ADDRESS,
				ipAddress: IP_ADDRESS)

		when:
		ThrottleResult throttleResult = throttleValidator.validate(Mock(Message))

		then:
		1 * requestCredentialProviderMock.provideRequestCredential() >> requestCredential
		1 * throttleRepositoryMock.decrementByIpAndGetResult(IP_ADDRESS) >> true
		0 * _
		!throttleResult.throttle
		throttleResult.throttleReason == null
	}

}
