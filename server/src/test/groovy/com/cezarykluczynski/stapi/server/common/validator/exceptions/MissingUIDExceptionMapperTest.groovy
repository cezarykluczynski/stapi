package com.cezarykluczynski.stapi.server.common.validator.exceptions

import jakarta.ws.rs.core.Response
import spock.lang.Specification

class MissingUIDExceptionMapperTest extends Specification {

	private MissingUIDExceptionMapper missingUIDExceptionMapper

	void setup() {
		missingUIDExceptionMapper = new MissingUIDExceptionMapper()
	}

	void "maps MissingUIDException to Response"() {
		given:
		MissingUIDException missingUIDException = new MissingUIDException()

		when:
		Response response = missingUIDExceptionMapper.toResponse(missingUIDException)

		then:
		response.status == Response.Status.BAD_REQUEST.statusCode
		((RestError) response.entity).code == 'MISSING_UID'
		((RestError) response.entity).description == 'UID is required'
	}

}
