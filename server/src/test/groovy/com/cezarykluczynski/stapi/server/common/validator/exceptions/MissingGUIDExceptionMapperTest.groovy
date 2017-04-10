package com.cezarykluczynski.stapi.server.common.validator.exceptions

import com.cezarykluczynski.stapi.server.common.throttle.rest.RestError
import spock.lang.Specification

import javax.ws.rs.core.Response

class MissingGUIDExceptionMapperTest extends Specification {

	private MissingGUIDExceptionMapper missingGUIDExceptionMapper

	void setup() {
		missingGUIDExceptionMapper = new MissingGUIDExceptionMapper()
	}

	void "maps MissingGUIDException to Response"() {
		given:
		MissingGUIDException missingGUIDException = new MissingGUIDException()

		when:
		Response response = missingGUIDExceptionMapper.toResponse(missingGUIDException)

		then:
		response.status == Response.Status.BAD_REQUEST.statusCode
		((RestError) response.entity).code == 'MISSING_GUID'
		((RestError) response.entity).description == 'GUID is required'
	}

}
