package com.cezarykluczynski.stapi.server.medical_condition.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType;
import com.cezarykluczynski.stapi.server.medical_condition.reader.MedicalConditionSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class MedicalConditionSoapEndpoint implements MedicalConditionPortType {

	public static final String ADDRESS = "/v1/soap/medicalCondition";

	private final MedicalConditionSoapReader seriesSoapReader;

	public MedicalConditionSoapEndpoint(MedicalConditionSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public MedicalConditionBaseResponse getMedicalConditionBase(@WebParam(partName = "request", name = "MedicalConditionBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/medicalCondition") MedicalConditionBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public MedicalConditionFullResponse getMedicalConditionFull(@WebParam(partName = "request", name = "MedicalConditionFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/medicalCondition") MedicalConditionFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
