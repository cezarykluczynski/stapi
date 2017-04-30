package com.cezarykluczynski.stapi.server.series.configuration

import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SeriesConfiguration seriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		seriesConfiguration = new SeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Series SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = seriesConfiguration.seriesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SeriesSoapEndpoint, SeriesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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
