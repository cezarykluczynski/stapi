package com.cezarykluczynski.stapi.server.book_series.configuration

import com.cezarykluczynski.stapi.server.book_series.endpoint.BookSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.book_series.endpoint.BookSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class BookSeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private BookSeriesConfiguration bookSeriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		bookSeriesConfiguration = new BookSeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "BookSeries SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = bookSeriesConfiguration.bookSeriesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(BookSeriesSoapEndpoint, BookSeriesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "BookSeries REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = bookSeriesConfiguration.bookSeriesServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(BookSeriesRestEndpoint, BookSeriesRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "BookSeriesBaseSoapMapper is created"() {
		when:
		BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper = bookSeriesConfiguration.bookSeriesBaseSoapMapper()

		then:
		bookSeriesBaseSoapMapper != null
	}

	void "BookSeriesFullSoapMapper is created"() {
		when:
		BookSeriesFullSoapMapper bookSeriesFullSoapMapper = bookSeriesConfiguration.bookSeriesFullSoapMapper()

		then:
		bookSeriesFullSoapMapper != null
	}

	void "BookSeriesBaseRestMapper is created"() {
		when:
		BookSeriesBaseRestMapper bookSeriesBaseRestMapper = bookSeriesConfiguration.bookSeriesBaseRestMapper()

		then:
		bookSeriesBaseRestMapper != null
	}

	void "BookSeriesFullRestMapper is created"() {
		when:
		BookSeriesFullRestMapper bookSeriesFullRestMapper = bookSeriesConfiguration.bookSeriesFullRestMapper()

		then:
		bookSeriesFullRestMapper != null
	}

}
