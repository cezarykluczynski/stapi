package com.cezarykluczynski.stapi.server.organization.configuration

import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationSoapEndpoint
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullSoapMapper
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationRestReader
import com.cezarykluczynski.stapi.server.organization.reader.OrganizationSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class OrganizationConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private OrganizationConfiguration organizationConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		organizationConfiguration = new OrganizationConfiguration(applicationContext: applicationContextMock)
	}

	void "Organization SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		OrganizationSoapReader organizationSoapReaderMock = Mock(OrganizationSoapReader)

		when:
		Endpoint organizationSoapEndpoint = organizationConfiguration.organizationSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(OrganizationSoapReader) >> organizationSoapReaderMock
		0 * _
		organizationSoapEndpoint != null
		((EndpointImpl) organizationSoapEndpoint).implementor instanceof OrganizationSoapEndpoint
		((EndpointImpl) organizationSoapEndpoint).bus == springBus
		organizationSoapEndpoint.published
	}

	void "OrganizationRestEndpoint is created"() {
		given:
		OrganizationRestReader organizationRestMapper = Mock(OrganizationRestReader)

		when:
		OrganizationRestEndpoint organizationRestEndpoint = organizationConfiguration.organizationRestEndpoint()

		then:
		1 * applicationContextMock.getBean(OrganizationRestReader) >> organizationRestMapper
		0 * _
		organizationRestEndpoint != null
		organizationRestEndpoint.organizationRestReader == organizationRestMapper
	}

	void "OrganizationBaseSoapMapper is created"() {
		when:
		OrganizationBaseSoapMapper organizationBaseSoapMapper = organizationConfiguration.organizationBaseSoapMapper()

		then:
		organizationBaseSoapMapper != null
	}

	void "OrganizationFullSoapMapper is created"() {
		when:
		OrganizationFullSoapMapper organizationFullSoapMapper = organizationConfiguration.organizationFullSoapMapper()

		then:
		organizationFullSoapMapper != null
	}

	void "OrganizationBaseRestMapper is created"() {
		when:
		OrganizationBaseRestMapper organizationBaseRestMapper = organizationConfiguration.organizationBaseRestMapper()

		then:
		organizationBaseRestMapper != null
	}

	void "OrganizationFullRestMapper is created"() {
		when:
		OrganizationFullRestMapper organizationFullRestMapper = organizationConfiguration.organizationFullRestMapper()

		then:
		organizationFullRestMapper != null
	}

}
