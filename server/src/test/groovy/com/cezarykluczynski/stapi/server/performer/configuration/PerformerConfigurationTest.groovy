package com.cezarykluczynski.stapi.server.performer.configuration

import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerSoapEndpoint
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper
import com.cezarykluczynski.stapi.server.performer.reader.PerformerSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class PerformerConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private PerformerConfiguration performerConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		performerConfiguration = new PerformerConfiguration(applicationContext: applicationContextMock)
	}

	def "performer soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		PerformerSoapReader performerSoapReaderMock = Mock(PerformerSoapReader)

		when:
		Endpoint performerSoapEndpoint = performerConfiguration.performerSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus.class) >> springBus
		1 * applicationContextMock.getBean(PerformerSoapReader.class) >> performerSoapReaderMock
		performerSoapEndpoint != null
		((EndpointImpl) performerSoapEndpoint).implementor instanceof PerformerSoapEndpoint
		((EndpointImpl) performerSoapEndpoint).bus == springBus
		performerSoapEndpoint.published
	}

	def "PerformerSoapMapper is created"() {
		when:
		PerformerSoapMapper performerSoapMapper = performerConfiguration.performerSoapMapper()

		then:
		performerSoapMapper != null
	}

	def "PerformerRestMapper is created"() {
		when:
		PerformerRestMapper performerRestMapper = performerConfiguration.performerRestMapper()

		then:
		performerRestMapper != null
	}

}
