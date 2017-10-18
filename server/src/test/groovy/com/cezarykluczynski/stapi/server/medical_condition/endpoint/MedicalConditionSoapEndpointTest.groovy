package com.cezarykluczynski.stapi.server.medical_condition.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse
import com.cezarykluczynski.stapi.server.medical_condition.reader.MedicalConditionSoapReader
import spock.lang.Specification

class MedicalConditionSoapEndpointTest extends Specification {

	private MedicalConditionSoapReader medicalConditionSoapReaderMock

	private MedicalConditionSoapEndpoint medicalConditionSoapEndpoint

	void setup() {
		medicalConditionSoapReaderMock = Mock()
		medicalConditionSoapEndpoint = new MedicalConditionSoapEndpoint(medicalConditionSoapReaderMock)
	}

	void "passes base call to MedicalConditionSoapReader"() {
		given:
		MedicalConditionBaseRequest medicalConditionRequest = Mock()
		MedicalConditionBaseResponse medicalConditionResponse = Mock()

		when:
		MedicalConditionBaseResponse medicalConditionResponseResult = medicalConditionSoapEndpoint.getMedicalConditionBase(medicalConditionRequest)

		then:
		1 * medicalConditionSoapReaderMock.readBase(medicalConditionRequest) >> medicalConditionResponse
		medicalConditionResponseResult == medicalConditionResponse
	}

	void "passes full call to MedicalConditionSoapReader"() {
		given:
		MedicalConditionFullRequest medicalConditionFullRequest = Mock()
		MedicalConditionFullResponse medicalConditionFullResponse = Mock()

		when:
		MedicalConditionFullResponse medicalConditionResponseResult = medicalConditionSoapEndpoint
				.getMedicalConditionFull(medicalConditionFullRequest)

		then:
		1 * medicalConditionSoapReaderMock.readFull(medicalConditionFullRequest) >> medicalConditionFullResponse
		medicalConditionResponseResult == medicalConditionFullResponse
	}

}
