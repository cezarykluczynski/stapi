package com.cezarykluczynski.stapi.server.common.validator

import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingGUIDException
import spock.lang.Specification

class StaticValidatorTest extends Specification {

	void "throws exception when GUID is missing"() {
		when:
		StaticValidator.requireGuid(null)

		then:
		thrown(MissingGUIDException)
	}

	void "does not throw exception when GUID is not null"() {
		when:
		StaticValidator.requireGuid('not null')

		then:
		notThrown(MissingGUIDException)
	}

}
