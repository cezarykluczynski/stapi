package com.cezarykluczynski.stapi.server.comicSeries.configuration

import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.comicSeries.reader.ComicSeriesRestReader
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

	void "ComicSeries SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		ComicSeriesSoapReader comicSeriesSoapReaderMock = Mock(ComicSeriesSoapReader)

		when:
		Endpoint comicSeriesSoapEndpoint = comicSeriesConfiguration.comicSeriesSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(ComicSeriesSoapReader) >> comicSeriesSoapReaderMock
		0 * _
		comicSeriesSoapEndpoint != null
		((EndpointImpl) comicSeriesSoapEndpoint).implementor instanceof ComicSeriesSoapEndpoint
		((EndpointImpl) comicSeriesSoapEndpoint).bus == springBus
		comicSeriesSoapEndpoint.published
	}

	void "ComicSeriesRestEndpoint is created"() {
		given:
		ComicSeriesRestReader comicSeriesRestMapper = Mock(ComicSeriesRestReader)

		when:
		ComicSeriesRestEndpoint comicSeriesRestEndpoint = comicSeriesConfiguration.comicSeriesRestEndpoint()

		then:
		1 * applicationContextMock.getBean(ComicSeriesRestReader) >> comicSeriesRestMapper
		0 * _
		comicSeriesRestEndpoint != null
		comicSeriesRestEndpoint.comicSeriesRestReader == comicSeriesRestMapper
	}

	void "ComicSeriesBaseSoapMapper is created"() {
		when:
		ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper = comicSeriesConfiguration.comicSeriesBaseSoapMapper()

		then:
		comicSeriesBaseSoapMapper != null
	}

	void "ComicSeriesFullSoapMapper is created"() {
		when:
		ComicSeriesFullSoapMapper comicSeriesFullSoapMapper = comicSeriesConfiguration.comicSeriesFullSoapMapper()

		then:
		comicSeriesFullSoapMapper != null
	}

	void "ComicSeriesBaseRestMapper is created"() {
		when:
		ComicSeriesBaseRestMapper comicSeriesBaseRestMapper = comicSeriesConfiguration.comicSeriesBaseRestMapper()

		then:
		comicSeriesBaseRestMapper != null
	}

	void "ComicSeriesFullRestMapper is created"() {
		when:
		ComicSeriesFullRestMapper comicSeriesFullRestMapper = comicSeriesConfiguration.comicSeriesFullRestMapper()

		then:
		comicSeriesFullRestMapper != null
	}

}
