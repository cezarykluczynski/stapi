package com.cezarykluczynski.stapi.server.season.configuration

import com.cezarykluczynski.stapi.server.season.endpoint.SeasonRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.season.endpoint.SeasonSoapEndpoint
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SeasonConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SeasonConfiguration seasonConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		seasonConfiguration = new SeasonConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Season SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = seasonConfiguration.seasonEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SeasonSoapEndpoint, SeasonSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Season REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = seasonConfiguration.seasonServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SeasonRestEndpoint, SeasonRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SeasonBaseSoapMapper is created"() {
		when:
		SeasonBaseSoapMapper seasonBaseSoapMapper = seasonConfiguration.seasonBaseSoapMapper()

		then:
		seasonBaseSoapMapper != null
	}

	void "SeasonFullSoapMapper is created"() {
		when:
		SeasonFullSoapMapper seasonFullSoapMapper = seasonConfiguration.seasonFullSoapMapper()

		then:
		seasonFullSoapMapper != null
	}

	void "SeasonBaseRestMapper is created"() {
		when:
		SeasonBaseRestMapper seasonBaseRestMapper = seasonConfiguration.seasonBaseRestMapper()

		then:
		seasonBaseRestMapper != null
	}

	void "SeasonFullRestMapper is created"() {
		when:
		SeasonFullRestMapper seasonFullRestMapper = seasonConfiguration.seasonFullRestMapper()

		then:
		seasonFullRestMapper != null
	}

}
