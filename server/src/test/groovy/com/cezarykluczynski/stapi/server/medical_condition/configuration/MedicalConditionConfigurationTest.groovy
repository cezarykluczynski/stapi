package com.cezarykluczynski.stapi.server.medical_condition.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionRestEndpoint
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class MedicalConditionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MedicalConditionConfiguration medicalConditionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		medicalConditionConfiguration = new MedicalConditionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "MedicalCondition REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = medicalConditionConfiguration.medicalConditionServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(MedicalConditionRestEndpoint, MedicalConditionRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "MedicalConditionBaseRestMapper is created"() {
		when:
		MedicalConditionBaseRestMapper medicalConditionBaseRestMapper = medicalConditionConfiguration.medicalConditionBaseRestMapper()

		then:
		medicalConditionBaseRestMapper != null
	}

	void "MedicalConditionFullRestMapper is created"() {
		when:
		MedicalConditionFullRestMapper medicalConditionFullRestMapper = medicalConditionConfiguration.medicalConditionFullRestMapper()

		then:
		medicalConditionFullRestMapper != null
	}

}
