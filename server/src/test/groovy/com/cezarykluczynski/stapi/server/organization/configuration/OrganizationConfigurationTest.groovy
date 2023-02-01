package com.cezarykluczynski.stapi.server.organization.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class OrganizationConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private OrganizationConfiguration organizationConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		organizationConfiguration = new OrganizationConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Organization REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = organizationConfiguration.organizationServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(OrganizationRestEndpoint, OrganizationRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "OrganizationBaseRestMapper is created"() {
		when:
		OrganizationBaseRestMapper organizationBaseRestMapper = organizationConfiguration.organizationBaseRestMapper()

		then:
		organizationBaseRestMapper != null
	}

	void "OrganizationFullRestMapper is created"() {
		when:
		OrganizationFullRestMapper organizationFullRestMapper = organizationConfiguration.organizationFullRestMapper()

		then:
		organizationFullRestMapper != null
	}

}
