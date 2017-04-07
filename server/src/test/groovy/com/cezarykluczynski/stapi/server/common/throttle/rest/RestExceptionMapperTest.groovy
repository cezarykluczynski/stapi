package com.cezarykluczynski.stapi.server.common.throttle.rest

import com.cezarykluczynski.stapi.server.common.throttle.ThrottleReason
import spock.lang.Specification

import javax.ws.rs.core.Response

class RestExceptionMapperTest extends Specification {

	private static final String MESSAGE = 'MESSAGE'
	private static final ThrottleReason THROTTLE_REASON = ThrottleReason.HOURLY_API_KEY_LIMIT_EXCEEDED

	private RestExceptionMapper restExceptionMapper

	void setup() {
		restExceptionMapper = new RestExceptionMapper()
	}

	void "maps RestException to Response"() {
		given:
		RestException restException = new RestException(MESSAGE, THROTTLE_REASON)

		when:
		Response response = restExceptionMapper.toResponse(restException)

		then:
		response.status == Response.Status.FORBIDDEN.statusCode
		((RestError) response.entity).code == THROTTLE_REASON.name()
		((RestError) response.entity).description == MESSAGE
	}

}
