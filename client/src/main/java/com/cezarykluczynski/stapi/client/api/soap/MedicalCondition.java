package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType;

public class MedicalCondition {

	private final MedicalConditionPortType medicalConditionPortType;

	public MedicalCondition(MedicalConditionPortType medicalConditionPortType) {
		this.medicalConditionPortType = medicalConditionPortType;
	}

	@Deprecated
	public MedicalConditionFullResponse get(MedicalConditionFullRequest request) {
		return medicalConditionPortType.getMedicalConditionFull(request);
	}

	@Deprecated
	public MedicalConditionBaseResponse search(MedicalConditionBaseRequest request) {
		return medicalConditionPortType.getMedicalConditionBase(request);
	}

}
