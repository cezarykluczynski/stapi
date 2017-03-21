package com.cezarykluczynski.stapi.server.series.configuration

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.series.reader.SeriesRestReader
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SeriesConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private SeriesConfiguration seriesConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		seriesConfiguration = new SeriesConfiguration(applicationContext: applicationContextMock)
	}

	void "Series SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		SeriesSoapReader seriesSoapReaderMock = Mock(SeriesSoapReader)

		when:
		Endpoint seriesSoapEndpoint = seriesConfiguration.seriesSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(SeriesSoapReader) >> seriesSoapReaderMock
		0 * _
		seriesSoapEndpoint != null
		((EndpointImpl) seriesSoapEndpoint).implementor instanceof SeriesSoapEndpoint
		((EndpointImpl) seriesSoapEndpoint).bus == springBus
		seriesSoapEndpoint.published
	}

	void "SeriesRestEndpoint is created"() {
		given:
		SeriesRestReader seriesRestMapper = Mock(SeriesRestReader)

		when:
		SeriesRestEndpoint seriesRestEndpoint = seriesConfiguration.seriesRestEndpoint()

		then:
		1 * applicationContextMock.getBean(SeriesRestReader) >> seriesRestMapper
		0 * _
		seriesRestEndpoint != null
		seriesRestEndpoint.seriesRestReader == seriesRestMapper
	}

	void "SeriesBaseSoapMapper is created"() {
		when:
		SeriesBaseSoapMapper seriesBaseSoapMapper = seriesConfiguration.seriesBaseSoapMapper()

		then:
		seriesBaseSoapMapper != null
	}

	void "SeriesFullSoapMapper is created"() {
		when:
		SeriesFullSoapMapper seriesFullSoapMapper = seriesConfiguration.seriesFullSoapMapper()

		then:
		seriesFullSoapMapper != null
	}

	void "SeriesBaseRestMapper is created"() {
		when:
		SeriesBaseRestMapper seriesBaseRestMapper = seriesConfiguration.seriesBaseRestMapper()

		then:
		seriesBaseRestMapper != null
	}

	void "SeriesFullRestMapper is created"() {
		when:
		SeriesFullRestMapper seriesFullRestMapper = seriesConfiguration.seriesFullRestMapper()

		then:
		seriesFullRestMapper != null
	}

}
