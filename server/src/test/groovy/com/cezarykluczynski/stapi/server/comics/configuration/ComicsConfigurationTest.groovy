package com.cezarykluczynski.stapi.server.comics.configuration

import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint
import com.cezarykluczynski.stapi.server.comics.reader.ComicsRestReader
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

	void "Comics SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicsSoapReader comicsSoapReaderMock = Mock(ComicsSoapReader)

		when:
		Endpoint comicsSoapEndpoint = comicsConfiguration.comicsSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicsSoapReader) >> comicsSoapReaderMock
		0 * _
		comicsSoapEndpoint != null
		((EndpointImpl) comicsSoapEndpoint).implementor instanceof ComicsSoapEndpoint
		((EndpointImpl) comicsSoapEndpoint).bus == springBus
		comicsSoapEndpoint.published
	}

	void "ComicsRestEndpoint is created"() {
		given:
		ComicsRestReader comicsRestMapper = Mock(ComicsRestReader)

		when:
		ComicsRestEndpoint comicsRestEndpoint = comicsConfiguration.comicsRestEndpoint()

		then:
		1 * applicationContextMock.getBean(ComicsRestReader) >> comicsRestMapper
		0 * _
		comicsRestEndpoint != null
		comicsRestEndpoint.comicsRestReader == comicsRestMapper
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
