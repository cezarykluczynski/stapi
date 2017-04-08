package com.cezarykluczynski.stapi.server.common.configuration

import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class CommonConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CommonConfiguration commonConfiguration

	void setup() {
		applicationContextMock = Mock()
		commonConfiguration = new CommonConfiguration(applicationContext: applicationContextMock)
	}

	void "PageMapper is created"() {
		when:
		PageMapper pageMapper = commonConfiguration.pageMapper()

		then:
		pageMapper != null
	}

}
