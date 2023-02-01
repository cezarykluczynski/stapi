package com.cezarykluczynski.stapi.server.reference.configuration

import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class ReferenceConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ReferenceConfiguration referenceConfiguration

	void setup() {
		applicationContextMock = Mock()
		referenceConfiguration = new ReferenceConfiguration()
	}

	void "ReferenceRestMapper is created"() {
		when:
		ReferenceRestMapper referenceRestMapper = referenceConfiguration.referenceRestMapper()

		then:
		referenceRestMapper != null
	}

}
