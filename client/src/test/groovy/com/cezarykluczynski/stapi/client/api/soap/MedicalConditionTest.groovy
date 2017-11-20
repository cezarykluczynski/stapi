package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType
import spock.lang.Specification

class MedicalConditionTest extends Specification {

	private MedicalConditionPortType medicalConditionPortTypeMock

	private ApiKeySupplier apiKeySupplierMock

	private MedicalCondition medicalCondition

	void setup() {
		medicalConditionPortTypeMock = Mock()
		apiKeySupplierMock = Mock()
		medicalCondition = new MedicalCondition(medicalConditionPortTypeMock, apiKeySupplierMock)
	}

	void "gets single entity"() {
		given:
		MedicalConditionBaseRequest medicalConditionBaseRequest = Mock()
		MedicalConditionBaseResponse medicalConditionBaseResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionBaseResponseOutput = medicalCondition.search(medicalConditionBaseRequest)

		then:
		1 * apiKeySupplierMock.supply(medicalConditionBaseRequest)
		1 * medicalConditionPortTypeMock.getMedicalConditionBase(medicalConditionBaseRequest) >> medicalConditionBaseResponse
		0 * _
		medicalConditionBaseResponse == medicalConditionBaseResponseOutput
	}

	void "searches entities"() {
		given:
		MedicalConditionFullRequest medicalConditionFullRequest = Mock()
		MedicalConditionFullResponse medicalConditionFullResponse = Mock()

		when:
		MedicalConditionFullResponse medicalConditionFullResponseOutput = medicalCondition.get(medicalConditionFullRequest)

		then:
		1 * apiKeySupplierMock.supply(medicalConditionFullRequest)
		1 * medicalConditionPortTypeMock.getMedicalConditionFull(medicalConditionFullRequest) >> medicalConditionFullResponse
		0 * _
		medicalConditionFullResponse == medicalConditionFullResponseOutput
	}

}
