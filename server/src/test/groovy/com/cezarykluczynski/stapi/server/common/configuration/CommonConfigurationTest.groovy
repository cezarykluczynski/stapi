package com.cezarykluczynski.stapi.server.common.configuration

import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class CommonConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CommonConfiguration commonConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		commonConfiguration = new CommonConfiguration()
		commonConfiguration.applicationContext = applicationContextMock
	}

	def "PageMapper is created"() {
		when:
		PageMapper pageMapper = commonConfiguration.pageMapper()

		then:
		pageMapper != null
	}

}
