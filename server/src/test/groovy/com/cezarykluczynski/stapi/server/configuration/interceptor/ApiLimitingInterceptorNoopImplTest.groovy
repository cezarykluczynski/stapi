package com.cezarykluczynski.stapi.server.configuration.interceptor

import org.apache.cxf.message.Message
import spock.lang.Specification

class ApiLimitingInterceptorNoopImplTest extends Specification {

	private ApiLimitingInterceptorNoopImpl apiLimitingInterceptorNoopImpl

	void setup() {
		apiLimitingInterceptorNoopImpl = new ApiLimitingInterceptorNoopImpl()
	}

	void "does nothing when message is handled"() {
		given:
		Message message = Mock(Message)

		when:
		apiLimitingInterceptorNoopImpl.handleMessage(message)

		then:
		0 * _
	}

	void "does nothing when fault is handled"() {
		given:
		Message message = Mock(Message)

		when:
		apiLimitingInterceptorNoopImpl.handleFault(message)

		then:
		0 * _
	}

}
