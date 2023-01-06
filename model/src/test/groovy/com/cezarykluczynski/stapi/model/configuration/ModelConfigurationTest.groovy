package com.cezarykluczynski.stapi.model.configuration

import org.springframework.context.ApplicationContext
import org.springframework.data.repository.support.Repositories
import spock.lang.Specification

class ModelConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ModelConfiguration modelConfiguration

	void setup() {
		applicationContextMock = Mock()
		modelConfiguration = new ModelConfiguration(
				applicationContext: applicationContextMock)
	}

	void "creates Repositories"() {
		when:
		Repositories repositories = modelConfiguration.repositories()

		then:
		1 * applicationContextMock.getBeanNamesForType(*_) >> new String[0]
		1 * applicationContextMock.parentBeanFactory >> null
		0 * _
		repositories != null
	}

}
