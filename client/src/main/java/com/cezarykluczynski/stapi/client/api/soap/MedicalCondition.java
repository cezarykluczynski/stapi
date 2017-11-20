package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType;

public class MedicalCondition {

	private final MedicalConditionPortType medicalConditionPortType;

	private final ApiKeySupplier apiKeySupplier;

	public MedicalCondition(MedicalConditionPortType medicalConditionPortType, ApiKeySupplier apiKeySupplier) {
		this.medicalConditionPortType = medicalConditionPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public MedicalConditionFullResponse get(MedicalConditionFullRequest request) {
		apiKeySupplier.supply(request);
		return medicalConditionPortType.getMedicalConditionFull(request);
	}

	public MedicalConditionBaseResponse search(MedicalConditionBaseRequest request) {
		apiKeySupplier.supply(request);
		return medicalConditionPortType.getMedicalConditionBase(request);
	}

}
