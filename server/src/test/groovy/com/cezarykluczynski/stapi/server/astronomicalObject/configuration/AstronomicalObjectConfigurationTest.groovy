package com.cezarykluczynski.stapi.server.astronomicalObject.configuration

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectRestReader
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

	void "AstronomicalObject SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		AstronomicalObjectSoapReader astronomicalObjectSoapReaderMock = Mock(AstronomicalObjectSoapReader)

		when:
		Endpoint astronomicalObjectSoapEndpoint = astronomicalObjectConfiguration.astronomicalObjectSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(AstronomicalObjectSoapReader) >> astronomicalObjectSoapReaderMock
		0 * _
		astronomicalObjectSoapEndpoint != null
		((EndpointImpl) astronomicalObjectSoapEndpoint).implementor instanceof AstronomicalObjectSoapEndpoint
		((EndpointImpl) astronomicalObjectSoapEndpoint).bus == springBus
		astronomicalObjectSoapEndpoint.published
	}

	void "AstronomicalObjectRestEndpoint is created"() {
		given:
		AstronomicalObjectRestReader astronomicalObjectRestMapper = Mock(AstronomicalObjectRestReader)

		when:
		AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint = astronomicalObjectConfiguration.astronomicalObjectRestEndpoint()

		then:
		1 * applicationContextMock.getBean(AstronomicalObjectRestReader) >> astronomicalObjectRestMapper
		0 * _
		astronomicalObjectRestEndpoint != null
		astronomicalObjectRestEndpoint.astronomicalObjectRestReader == astronomicalObjectRestMapper
	}

	void "AstronomicalObjectBaseSoapMapper is created"() {
		when:
		AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper = astronomicalObjectConfiguration.astronomicalObjectBaseSoapMapper()

		then:
		astronomicalObjectBaseSoapMapper != null
	}

	void "AstronomicalObjectFullSoapMapper is created"() {
		when:
		AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper = astronomicalObjectConfiguration.astronomicalObjectFullSoapMapper()

		then:
		astronomicalObjectFullSoapMapper != null
	}

	void "AstronomicalObjectBaseRestMapper is created"() {
		when:
		AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper = astronomicalObjectConfiguration.astronomicalObjectBaseRestMapper()

		then:
		astronomicalObjectBaseRestMapper != null
	}

	void "AstronomicalObjectFullRestMapper is created"() {
		when:
		AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper = astronomicalObjectConfiguration.astronomicalObjectFullRestMapper()

		then:
		astronomicalObjectFullRestMapper != null
	}

}
