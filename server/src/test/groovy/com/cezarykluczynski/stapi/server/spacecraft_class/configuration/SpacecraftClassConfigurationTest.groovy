package com.cezarykluczynski.stapi.server.spacecraft_class.configuration

import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.spacecraft_class.endpoint.SpacecraftClassSoapEndpoint
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class SpacecraftClassConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private SpacecraftClassConfiguration spacecraftClassConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		spacecraftClassConfiguration = new SpacecraftClassConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "SpacecraftClass SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = spacecraftClassConfiguration.spacecraftClassEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(SpacecraftClassSoapEndpoint, SpacecraftClassSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "SpacecraftClass REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = spacecraftClassConfiguration.spacecraftClassServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(SpacecraftClassRestEndpoint, SpacecraftClassRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "SpacecraftClassBaseSoapMapper is created"() {
		when:
		SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper = spacecraftClassConfiguration.spacecraftClassBaseSoapMapper()

		then:
		spacecraftClassBaseSoapMapper != null
	}

	void "SpacecraftClassFullSoapMapper is created"() {
		when:
		SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper = spacecraftClassConfiguration.spacecraftClassFullSoapMapper()

		then:
		spacecraftClassFullSoapMapper != null
	}

	void "SpacecraftClassBaseRestMapper is created"() {
		when:
		SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper = spacecraftClassConfiguration.spacecraftClassBaseRestMapper()

		then:
		spacecraftClassBaseRestMapper != null
	}

	void "SpacecraftClassFullRestMapper is created"() {
		when:
		SpacecraftClassFullRestMapper spacecraftClassFullRestMapper = spacecraftClassConfiguration.spacecraftClassFullRestMapper()

		then:
		spacecraftClassFullRestMapper != null
	}

}
