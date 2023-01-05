package com.cezarykluczynski.stapi.server.magazine_series.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.magazine_series.endpoint.MagazineSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.magazine_series.endpoint.MagazineSeriesSoapEndpoint
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullRestMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper
import jakarta.xml.ws.Endpoint
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class MagazineSeriesConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MagazineSeriesConfiguration magazineSeriesConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		magazineSeriesConfiguration = new MagazineSeriesConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "MagazineSeries SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = magazineSeriesConfiguration.magazineSeriesEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(MagazineSeriesSoapEndpoint, MagazineSeriesSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "MagazineSeries REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = magazineSeriesConfiguration.magazineSeriesServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(MagazineSeriesRestEndpoint, MagazineSeriesRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "MagazineSeriesBaseSoapMapper is created"() {
		when:
		MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper = magazineSeriesConfiguration.magazineSeriesBaseSoapMapper()

		then:
		magazineSeriesBaseSoapMapper != null
	}

	void "MagazineSeriesFullSoapMapper is created"() {
		when:
		MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper = magazineSeriesConfiguration.magazineSeriesFullSoapMapper()

		then:
		magazineSeriesFullSoapMapper != null
	}

	void "MagazineSeriesBaseRestMapper is created"() {
		when:
		MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper = magazineSeriesConfiguration.magazineSeriesBaseRestMapper()

		then:
		magazineSeriesBaseRestMapper != null
	}

	void "MagazineSeriesFullRestMapper is created"() {
		when:
		MagazineSeriesFullRestMapper magazineSeriesFullRestMapper = magazineSeriesConfiguration.magazineSeriesFullRestMapper()

		then:
		magazineSeriesFullRestMapper != null
	}

}
