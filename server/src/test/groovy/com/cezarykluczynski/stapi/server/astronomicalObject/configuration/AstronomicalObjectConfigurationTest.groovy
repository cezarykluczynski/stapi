package com.cezarykluczynski.stapi.server.astronomicalObject.configuration

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class AstronomicalObjectConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private AstronomicalObjectConfiguration astronomicalObjectConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		astronomicalObjectConfiguration = new AstronomicalObjectConfiguration(applicationContext: applicationContextMock)
	}

	void "astronomicalObject soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		AstronomicalObjectSoapReader astronomicalObjectSoapReaderMock = Mock(AstronomicalObjectSoapReader)

		when:
		Endpoint astronomicalObjectSoapEndpoint = astronomicalObjectConfiguration.astronomicalObjectSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(AstronomicalObjectSoapReader) >> astronomicalObjectSoapReaderMock
		astronomicalObjectSoapEndpoint != null
		((EndpointImpl) astronomicalObjectSoapEndpoint).implementor instanceof AstronomicalObjectSoapEndpoint
		((EndpointImpl) astronomicalObjectSoapEndpoint).bus == springBus
		astronomicalObjectSoapEndpoint.published
	}

	void "AstronomicalObjectSoapMapper is created"() {
		when:
		AstronomicalObjectSoapMapper astronomicalObjectSoapMapper = astronomicalObjectConfiguration.astronomicalObjectSoapMapper()

		then:
		astronomicalObjectSoapMapper != null
	}

	void "AstronomicalObjectRestMapper is created"() {
		when:
		AstronomicalObjectRestMapper astronomicalObjectRestMapper = astronomicalObjectConfiguration.astronomicalObjectRestMapper()

		then:
		astronomicalObjectRestMapper != null
	}

}
