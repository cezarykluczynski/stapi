package com.cezarykluczynski.stapi.server.series.configuration

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SeriesConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private SeriesConfiguration seriesConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		seriesConfiguration = new SeriesConfiguration()
		seriesConfiguration.applicationContext = applicationContextMock
	}

	def "series soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		SeriesSoapReader seriesSoapReaderMock = Mock(SeriesSoapReader)

		when:
		Endpoint seriesSoapEndpoint = seriesConfiguration.seriesSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus.class) >> springBus
		1 * applicationContextMock.getBean(SeriesSoapReader.class) >> seriesSoapReaderMock
		seriesSoapEndpoint != null
		((EndpointImpl) seriesSoapEndpoint).implementor instanceof SeriesSoapEndpoint
		((EndpointImpl) seriesSoapEndpoint).bus == springBus
		seriesSoapEndpoint.published
	}

	def "SeriesSoapMapper is created"() {
		when:
		SeriesSoapMapper seriesSoapMapper = seriesConfiguration.seriesSoapMapper()

		then:
		seriesSoapMapper != null
	}

	def "SeriesRestMapper is created"() {
		when:
		SeriesRestMapper seriesRestMapper = seriesConfiguration.seriesRestMapper()

		then:
		seriesRestMapper != null
	}

}
