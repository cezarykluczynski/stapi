package com.cezarykluczynski.stapi.server.company.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyV2RestEndpoint
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper
import com.cezarykluczynski.stapi.server.company.mapper.CompanyFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class CompanyConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private CompanyConfiguration companyConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		companyConfiguration = new CompanyConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Company REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = companyConfiguration.companyServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(CompanyRestEndpoint, CompanyRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Company V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = companyConfiguration.companyV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(CompanyV2RestEndpoint, CompanyV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "CompanyBaseRestMapper is created"() {
		when:
		CompanyBaseRestMapper companyBaseRestMapper = companyConfiguration.companyBaseRestMapper()

		then:
		companyBaseRestMapper != null
	}

	void "CompanyFullRestMapper is created"() {
		when:
		CompanyFullRestMapper companyFullRestMapper = companyConfiguration.companyFullRestMapper()

		then:
		companyFullRestMapper != null
	}

}
