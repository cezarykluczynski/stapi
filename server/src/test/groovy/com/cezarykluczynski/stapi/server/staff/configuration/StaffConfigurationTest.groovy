package com.cezarykluczynski.stapi.server.staff.configuration

import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffSoapEndpoint
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class StaffConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private StaffConfiguration staffConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		staffConfiguration = new StaffConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Staff SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = staffConfiguration.staffEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(StaffSoapEndpoint, StaffSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "StaffBaseSoapMapper is created"() {
		when:
		StaffBaseSoapMapper staffBaseSoapMapper = staffConfiguration.staffBaseSoapMapper()

		then:
		staffBaseSoapMapper != null
	}

	void "StaffFullSoapMapper is created"() {
		when:
		StaffFullSoapMapper staffFullSoapMapper = staffConfiguration.staffFullSoapMapper()

		then:
		staffFullSoapMapper != null
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
