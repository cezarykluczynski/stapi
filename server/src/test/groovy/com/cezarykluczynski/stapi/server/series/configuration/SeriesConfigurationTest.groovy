package com.cezarykluczynski.stapi.server.series.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class SeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SeriesConfiguration seriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		seriesConfiguration = new SeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Series REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = seriesConfiguration.seriesServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SeriesRestEndpoint, SeriesRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
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
