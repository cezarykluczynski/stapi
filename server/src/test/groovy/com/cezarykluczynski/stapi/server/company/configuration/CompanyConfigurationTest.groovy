package com.cezarykluczynski.stapi.server.company.configuration

import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint
import com.cezarykluczynski.stapi.server.company.reader.CompanyRestReader
import com.cezarykluczynski.stapi.server.company.endpoint.CompanySoapEndpoint
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper
import com.cezarykluczynski.stapi.server.company.reader.CompanySoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class CompanyConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CompanyConfiguration companyConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		companyConfiguration = new CompanyConfiguration(applicationContext: applicationContextMock)
	}

	void "Company SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		CompanySoapReader companySoapReaderMock = Mock(CompanySoapReader)

		when:
		Endpoint companySoapEndpoint = companyConfiguration.companySoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(CompanySoapReader) >> companySoapReaderMock
		0 * _
		companySoapEndpoint != null
		((EndpointImpl) companySoapEndpoint).implementor instanceof CompanySoapEndpoint
		((EndpointImpl) companySoapEndpoint).bus == springBus
		companySoapEndpoint.published
	}

	void "CompanyRestEndpoint is created"() {
		given:
		CompanyRestReader companyRestMapper = Mock(CompanyRestReader)

		when:
		CompanyRestEndpoint companyRestEndpoint = companyConfiguration.companyRestEndpoint()

		then:
		1 * applicationContextMock.getBean(CompanyRestReader) >> companyRestMapper
		0 * _
		companyRestEndpoint != null
		companyRestEndpoint.companyRestReader == companyRestMapper
	}

	void "CompanySoapMapper is created"() {
		when:
		CompanySoapMapper companySoapMapper = companyConfiguration.companySoapMapper()

		then:
		companySoapMapper != null
	}

	void "CompanyRestMapper is created"() {
		when:
		CompanyRestMapper companyRestMapper = companyConfiguration.companyRestMapper()

		then:
		companyRestMapper != null
	}

}
