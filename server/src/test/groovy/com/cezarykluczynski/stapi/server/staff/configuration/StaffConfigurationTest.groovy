package com.cezarykluczynski.stapi.server.staff.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffV2RestEndpoint
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class StaffConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private StaffConfiguration staffConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		staffConfiguration = new StaffConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Staff REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = staffConfiguration.staffServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(StaffRestEndpoint, StaffRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "Staff V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = staffConfiguration.staffV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(StaffV2RestEndpoint, StaffV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "StaffBaseRestMapper is created"() {
		when:
		StaffBaseRestMapper staffBaseRestMapper = staffConfiguration.staffBaseRestMapper()

		then:
		staffBaseRestMapper != null
	}

	void "StaffFullRestMapper is created"() {
		when:
		StaffFullRestMapper staffFullRestMapper = staffConfiguration.staffFullRestMapper()

		then:
		staffFullRestMapper != null
	}

}
