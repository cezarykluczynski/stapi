package com.cezarykluczynski.stapi.server.comic_series.configuration

import com.cezarykluczynski.stapi.server.comic_series.endpoint.ComicSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class ComicSeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private ComicSeriesConfiguration comicSeriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		comicSeriesConfiguration = new ComicSeriesConfiguration(endpointFactory: endpointFactoryMock)
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
