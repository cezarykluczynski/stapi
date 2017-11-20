package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType;

public class Technology {

	private final TechnologyPortType technologyPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Technology(TechnologyPortType technologyPortType, ApiKeySupplier apiKeySupplier) {
		this.technologyPortType = technologyPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public TechnologyFullResponse get(TechnologyFullRequest request) {
		apiKeySupplier.supply(request);
		return technologyPortType.getTechnologyFull(request);
	}

	public TechnologyBaseResponse search(TechnologyBaseRequest request) {
		apiKeySupplier.supply(request);
		return technologyPortType.getTechnologyBase(request);
	}

}

