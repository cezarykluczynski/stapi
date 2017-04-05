package com.cezarykluczynski.stapi.server.configuration.interceptor

import com.cezarykluczynski.stapi.server.common.throttle.ThrottleFacade
import org.apache.cxf.message.Message
import spock.lang.Specification

class ApiThrottlingInterceptorImplTest extends Specification {

	private ThrottleFacade throttleFacadeMock

	private ApiThrottlingInterceptorImpl apiThrottlingInterceptorImpl

	void setup() {
		throttleFacadeMock = Mock(ThrottleFacade)
		apiThrottlingInterceptorImpl = new ApiThrottlingInterceptorImpl(throttleFacadeMock)
	}

	void "passes message validation to ThrottleFacade"() {
		given:
		Message message = Mock(Message)

		when:
		apiThrottlingInterceptorImpl.handleMessage(message)

		then:
		1 * throttleFacadeMock.validate(message)
		0 * _
	}

	void "passes fault validation to ThrottleFacade"() {
		given:
		Message message = Mock(Message)

		when:
		apiThrottlingInterceptorImpl.handleFault(message)

		then:
		1 * throttleFacadeMock.validate(message)
		0 * _
	}

}
