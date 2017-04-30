package com.cezarykluczynski.stapi.server.comicSeries.configuration

import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class ComicSeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicSeriesConfiguration comicSeriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicSeriesConfiguration = new ComicSeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "ComicSeries SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = comicSeriesConfiguration.comicSeriesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(ComicSeriesSoapEndpoint, ComicSeriesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "ComicSeries REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = comicSeriesConfiguration.comicSeriesServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(ComicSeriesRestEndpoint, ComicSeriesRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
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
