package com.cezarykluczynski.stapi.server.comicStrip.configuration

import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripSoapEndpoint
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper
import com.cezarykluczynski.stapi.server.comicStrip.reader.ComicStripSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicStripConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ComicStripConfiguration comicStripConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		comicStripConfiguration = new ComicStripConfiguration(applicationContext: applicationContextMock)
	}

	void "comicStrip soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicStripSoapReader comicStripSoapReaderMock = Mock(ComicStripSoapReader)

		when:
		Endpoint comicStripSoapEndpoint = comicStripConfiguration.comicStripSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicStripSoapReader) >> comicStripSoapReaderMock
		comicStripSoapEndpoint != null
		((EndpointImpl) comicStripSoapEndpoint).implementor instanceof ComicStripSoapEndpoint
		((EndpointImpl) comicStripSoapEndpoint).bus == springBus
		comicStripSoapEndpoint.published
	}

	void "ComicStripSoapMapper is created"() {
		when:
		ComicStripSoapMapper comicStripSoapMapper = comicStripConfiguration.comicStripSoapMapper()

		then:
		comicStripSoapMapper != null
	}

	void "ComicStripRestMapper is created"() {
		when:
		ComicStripRestMapper comicStripRestMapper = comicStripConfiguration.comicStripRestMapper()

		then:
		comicStripRestMapper != null
	}

}
