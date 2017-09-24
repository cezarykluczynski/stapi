package com.cezarykluczynski.stapi.server.material.configuration

import com.cezarykluczynski.stapi.server.material.endpoint.MaterialRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.material.endpoint.MaterialSoapEndpoint
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class MaterialConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MaterialConfiguration materialConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		materialConfiguration = new MaterialConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Material SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = materialConfiguration.materialEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(MaterialSoapEndpoint, MaterialSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "MaterialBaseSoapMapper is created"() {
		when:
		MaterialBaseSoapMapper materialBaseSoapMapper = materialConfiguration.materialBaseSoapMapper()

		then:
		materialBaseSoapMapper != null
	}

	void "MaterialFullSoapMapper is created"() {
		when:
		MaterialFullSoapMapper materialFullSoapMapper = materialConfiguration.materialFullSoapMapper()

		then:
		materialFullSoapMapper != null
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
