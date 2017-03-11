package com.cezarykluczynski.stapi.server.comicCollection.configuration

import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionRestReader
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionSoapEndpoint
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionRestMapper
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionSoapMapper
import com.cezarykluczynski.stapi.server.comicCollection.reader.ComicCollectionSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicCollectionConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ComicCollectionConfiguration comicCollectionConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		comicCollectionConfiguration = new ComicCollectionConfiguration(applicationContext: applicationContextMock)
	}

	void "ComicCollection SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicCollectionSoapReader comicCollectionSoapReaderMock = Mock(ComicCollectionSoapReader)

		when:
		Endpoint comicCollectionSoapEndpoint = comicCollectionConfiguration.comicCollectionSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicCollectionSoapReader) >> comicCollectionSoapReaderMock
		0 * _
		comicCollectionSoapEndpoint != null
		((EndpointImpl) comicCollectionSoapEndpoint).implementor instanceof ComicCollectionSoapEndpoint
		((EndpointImpl) comicCollectionSoapEndpoint).bus == springBus
		comicCollectionSoapEndpoint.published
	}

	void "ComicCollectionRestEndpoint is created"() {
		given:
		ComicCollectionRestReader comicCollectionRestMapper = Mock(ComicCollectionRestReader)

		when:
		ComicCollectionRestEndpoint comicCollectionRestEndpoint = comicCollectionConfiguration.comicCollectionRestEndpoint()

		then:
		1 * applicationContextMock.getBean(ComicCollectionRestReader) >> comicCollectionRestMapper
		0 * _
		comicCollectionRestEndpoint != null
		comicCollectionRestEndpoint.comicCollectionRestReader == comicCollectionRestMapper
	}

	void "ComicCollectionSoapMapper is created"() {
		when:
		ComicCollectionSoapMapper comicCollectionSoapMapper = comicCollectionConfiguration.comicCollectionSoapMapper()

		then:
		comicCollectionSoapMapper != null
	}

	void "ComicCollectionRestMapper is created"() {
		when:
		ComicCollectionRestMapper comicCollectionRestMapper = comicCollectionConfiguration.comicCollectionRestMapper()

		then:
		comicCollectionRestMapper != null
	}

}
