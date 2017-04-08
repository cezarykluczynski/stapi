package com.cezarykluczynski.stapi.server.configuration.interceptor

import org.apache.cxf.message.Message
import spock.lang.Specification

class ApiThrottlingInterceptorNoopImplTest extends Specification {

	private ApiThrottlingInterceptorNoopImpl apiThrottlingInterceptorNoopImpl

	void setup() {
		apiThrottlingInterceptorNoopImpl = new ApiThrottlingInterceptorNoopImpl()
	}

	void "does nothing when message is handled"() {
		given:
		Message message = Mock()

		when:
		apiThrottlingInterceptorNoopImpl.handleMessage(message)

		then:
		0 * _
	}

	void "does nothing when fault is handled"() {
		given:
		Message message = Mock()

		when:
		apiThrottlingInterceptorNoopImpl.handleFault(message)

		then:
		0 * _
	}

}
