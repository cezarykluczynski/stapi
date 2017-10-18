package com.cezarykluczynski.stapi.server.medical_condition.configuration

import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.medical_condition.endpoint.MedicalConditionSoapEndpoint
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseRestMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionBaseSoapMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullRestMapper
import com.cezarykluczynski.stapi.server.medical_condition.mapper.MedicalConditionFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class MedicalConditionConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MedicalConditionConfiguration medicalConditionConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		medicalConditionConfiguration = new MedicalConditionConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "MedicalCondition SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = medicalConditionConfiguration.medicalConditionEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(MedicalConditionSoapEndpoint, MedicalConditionSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "MedicalConditionBaseSoapMapper is created"() {
		when:
		MedicalConditionBaseSoapMapper medicalConditionBaseSoapMapper = medicalConditionConfiguration.medicalConditionBaseSoapMapper()

		then:
		medicalConditionBaseSoapMapper != null
	}

	void "MedicalConditionFullSoapMapper is created"() {
		when:
		MedicalConditionFullSoapMapper medicalConditionFullSoapMapper = medicalConditionConfiguration.medicalConditionFullSoapMapper()

		then:
		medicalConditionFullSoapMapper != null
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
