package com.cezarykluczynski.stapi.model.configuration

import com.cezarykluczynski.stapi.model.common.etl.EtlProperties
import org.springframework.context.ApplicationContext
import org.springframework.data.repository.support.Repositories
import spock.lang.Specification

class ModelConfigurationTest extends Specification {

	private HibernateProperties hibernatePropertiesMock

	private ApplicationContext applicationContextMock

	private EtlProperties etlPropertiesMock

	private ModelConfiguration modelConfiguration

	void setup() {
		hibernatePropertiesMock = Mock()
		applicationContextMock = Mock()
		etlPropertiesMock = Mock()
		modelConfiguration = new ModelConfiguration(
				hibernateProperties: hibernatePropertiesMock,
				applicationContext: applicationContextMock,
				etlProperties: etlPropertiesMock)
	}

	void "creates SpringLiquibase mock"() {
		when:
		Object liquibaseBase = modelConfiguration.liquibaseMock()

		then:
		liquibaseBase != null
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
