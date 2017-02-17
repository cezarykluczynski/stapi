package com.cezarykluczynski.stapi.server.comics.configuration

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsSoapEndpoint
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsRestMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsSoapMapper
import com.cezarykluczynski.stapi.server.comics.reader.ComicsSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicsConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ComicsConfiguration comicsConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		comicsConfiguration = new ComicsConfiguration(applicationContext: applicationContextMock)
	}

	void "comics soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicsSoapReader comicsSoapReaderMock = Mock(ComicsSoapReader)

		when:
		Endpoint comicsSoapEndpoint = comicsConfiguration.comicsSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicsSoapReader) >> comicsSoapReaderMock
		comicsSoapEndpoint != null
		((EndpointImpl) comicsSoapEndpoint).implementor instanceof ComicsSoapEndpoint
		((EndpointImpl) comicsSoapEndpoint).bus == springBus
		comicsSoapEndpoint.published
	}

	void "ComicsSoapMapper is created"() {
		when:
		ComicsSoapMapper comicsSoapMapper = comicsConfiguration.comicsSoapMapper()

		then:
		comicsSoapMapper != null
	}

	void "ComicsRestMapper is created"() {
		when:
		ComicsRestMapper comicsRestMapper = comicsConfiguration.comicsRestMapper()

		then:
		comicsRestMapper != null
	}

}
