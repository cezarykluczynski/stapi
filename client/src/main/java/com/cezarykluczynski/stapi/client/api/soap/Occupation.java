package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType;

public class Occupation {

	private final OccupationPortType occupationPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Occupation(OccupationPortType occupationPortType, ApiKeySupplier apiKeySupplier) {
		this.occupationPortType = occupationPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public OccupationFullResponse get(OccupationFullRequest request) {
		apiKeySupplier.supply(request);
		return occupationPortType.getOccupationFull(request);
	}

	public OccupationBaseResponse search(OccupationBaseRequest request) {
		apiKeySupplier.supply(request);
		return occupationPortType.getOccupationBase(request);
	}

}
