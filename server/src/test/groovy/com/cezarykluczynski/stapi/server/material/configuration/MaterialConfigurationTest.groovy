package com.cezarykluczynski.stapi.server.material.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.material.endpoint.MaterialRestEndpoint
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class MaterialConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MaterialConfiguration materialConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		materialConfiguration = new MaterialConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Material REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = materialConfiguration.materialServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(MaterialRestEndpoint, MaterialRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "MaterialBaseRestMapper is created"() {
		when:
		MaterialBaseRestMapper materialBaseRestMapper = materialConfiguration.materialBaseRestMapper()

		then:
		materialBaseRestMapper != null
	}

	void "MaterialFullRestMapper is created"() {
		when:
		MaterialFullRestMapper materialFullRestMapper = materialConfiguration.materialFullRestMapper()

		then:
		materialFullRestMapper != null
	}

}
