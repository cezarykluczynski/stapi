package com.cezarykluczynski.stapi.server.configuration.interceptor

import org.apache.cxf.message.Message
import spock.lang.Specification

class ApiThrottleLimitHeadersBindingInterceptorNoopImplTest extends Specification {

	private ApiThrottleLimitHeadersBindingInterceptorNoopImpl apiThrottleLimitHeadersBindingInterceptorNoopImpl

	void setup() {
		apiThrottleLimitHeadersBindingInterceptorNoopImpl = new ApiThrottleLimitHeadersBindingInterceptorNoopImpl()
	}

	void "does nothing when message is handled"() {
		given:
		Message message = Mock()

		when:
		apiThrottleLimitHeadersBindingInterceptorNoopImpl.handleMessage(message)

		then:
		0 * _
	}

	void "does nothing when fault is handled"() {
		given:
		Message message = Mock()

		when:
		apiThrottleLimitHeadersBindingInterceptorNoopImpl.handleFault(message)

		then:
		0 * _
	}

}
