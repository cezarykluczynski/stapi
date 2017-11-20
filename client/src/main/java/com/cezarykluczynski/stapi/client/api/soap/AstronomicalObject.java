package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;

public class AstronomicalObject {

	private final AstronomicalObjectPortType astronomicalObjectPortType;

	private final ApiKeySupplier apiKeySupplier;

	public AstronomicalObject(AstronomicalObjectPortType astronomicalObjectPortType, ApiKeySupplier apiKeySupplier) {
		this.astronomicalObjectPortType = astronomicalObjectPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public AstronomicalObjectFullResponse get(AstronomicalObjectFullRequest request) {
		apiKeySupplier.supply(request);
		return astronomicalObjectPortType.getAstronomicalObjectFull(request);
	}

	public AstronomicalObjectBaseResponse search(AstronomicalObjectBaseRequest request) {
		apiKeySupplier.supply(request);
		return astronomicalObjectPortType.getAstronomicalObjectBase(request);
	}

}
