package com.cezarykluczynski.stapi.server.common.validator

import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import spock.lang.Specification

class StaticValidatorTest extends Specification {

	void "throws exception when UID is missing"() {
		when:
		StaticValidator.requireUid(null)

		then:
		thrown(MissingUIDException)
	}

	void "does not throw exception when UID is not null"() {
		when:
		StaticValidator.requireUid('not null')

		then:
		notThrown(MissingUIDException)
	}

}
