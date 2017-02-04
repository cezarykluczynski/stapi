package com.cezarykluczynski.stapi.server.comicSeries.configuration

import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicSeriesConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private ComicSeriesConfiguration comicSeriesConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		comicSeriesConfiguration = new ComicSeriesConfiguration(applicationContext: applicationContextMock)
	}

	void "comicSeries soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicSeriesSoapReader comicSeriesSoapReaderMock = Mock(ComicSeriesSoapReader)

		when:
		Endpoint comicSeriesSoapEndpoint = comicSeriesConfiguration.comicSeriesSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicSeriesSoapReader) >> comicSeriesSoapReaderMock
		comicSeriesSoapEndpoint != null
		((EndpointImpl) comicSeriesSoapEndpoint).implementor instanceof ComicSeriesSoapEndpoint
		((EndpointImpl) comicSeriesSoapEndpoint).bus == springBus
		comicSeriesSoapEndpoint.published
	}

	void "ComicSeriesSoapMapper is created"() {
		when:
		ComicSeriesSoapMapper comicSeriesSoapMapper = comicSeriesConfiguration.comicSeriesSoapMapper()

		then:
		comicSeriesSoapMapper != null
	}

	void "ComicSeriesRestMapper is created"() {
		when:
		ComicSeriesRestMapper comicSeriesRestMapper = comicSeriesConfiguration.comicSeriesRestMapper()

		then:
		comicSeriesRestMapper != null
	}

}
