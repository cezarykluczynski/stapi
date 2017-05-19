package com.cezarykluczynski.stapi.server.configuration.interceptor

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics
import com.cezarykluczynski.stapi.server.common.throttle.RequestSpecificThrottleStatistics
import org.apache.cxf.message.Message
import org.apache.cxf.message.MessageImpl
import spock.lang.Specification

class ApiThrottleLimitHeadersBindingInterceptorImplTest extends Specification {

	private static final Integer TOTAL = 100
	private static final Integer REMAINING = 50

	private RequestSpecificThrottleStatistics requestSpecificThrottleStatisticsMock

	private ApiThrottleLimitHeadersBindingInterceptorImpl apiThrottleLimitHeadersBindingInterceptorImpl

	void setup() {
		requestSpecificThrottleStatisticsMock = Mock()
		apiThrottleLimitHeadersBindingInterceptorImpl = new ApiThrottleLimitHeadersBindingInterceptorImpl(requestSpecificThrottleStatisticsMock)
	}

	@SuppressWarnings('LineLength')
	void "when message is handled and throttle statistics are found, headers are set"() {
		given:
		Message message = new MessageImpl()
		ThrottleStatistics throttleStatistics = Mock()

		when:
		apiThrottleLimitHeadersBindingInterceptorImpl.handleMessage(message)

		then:
		1 * requestSpecificThrottleStatisticsMock.throttleStatistics >> throttleStatistics
		1 * throttleStatistics.total >> TOTAL
		1 * throttleStatistics.remaining >> REMAINING
		0 * _
		((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)).get(ApiThrottleLimitHeadersBindingInterceptorImpl.X_THROTTLE_LIMIT_TOTAL)[0] == "${TOTAL}"
		((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)).get(ApiThrottleLimitHeadersBindingInterceptorImpl.X_THROTTLE_LIMIT_REMAINING)[0] == "${REMAINING}"
	}

	void "when message is handled and throttle statistics are not found, headers are not set"() {
		given:
		Message message = new MessageImpl()
		when:
		apiThrottleLimitHeadersBindingInterceptorImpl.handleMessage(message)

		then:
		1 * requestSpecificThrottleStatisticsMock.throttleStatistics >> null
		0 * _
		message.get(Message.PROTOCOL_HEADERS) == null
	}

	@SuppressWarnings('LineLength')
	void "when fault is handled and throttle statistics are found, headers are set"() {
		given:
		Message message = new MessageImpl()
		ThrottleStatistics throttleStatistics = Mock()

		when:
		apiThrottleLimitHeadersBindingInterceptorImpl.handleFault(message)

		then:
		1 * requestSpecificThrottleStatisticsMock.throttleStatistics >> throttleStatistics
		1 * throttleStatistics.total >> TOTAL
		1 * throttleStatistics.remaining >> REMAINING
		0 * _
		((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)).get(ApiThrottleLimitHeadersBindingInterceptorImpl.X_THROTTLE_LIMIT_TOTAL)[0] == "${TOTAL}"
		((Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS)).get(ApiThrottleLimitHeadersBindingInterceptorImpl.X_THROTTLE_LIMIT_REMAINING)[0] == "${REMAINING}"
	}

	void "when fault is handled and throttle statistics are not found, headers are not set"() {
		given:
		Message message = new MessageImpl()
		when:
		apiThrottleLimitHeadersBindingInterceptorImpl.handleFault(message)

		then:
		1 * requestSpecificThrottleStatisticsMock.throttleStatistics >> null
		0 * _
		message.get(Message.PROTOCOL_HEADERS) == null
	}

}
