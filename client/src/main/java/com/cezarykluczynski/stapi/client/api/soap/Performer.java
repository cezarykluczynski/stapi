package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;

public class Performer {

	private final PerformerPortType performerPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Performer(PerformerPortType performerPortType, ApiKeySupplier apiKeySupplier) {
		this.performerPortType = performerPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public PerformerFullResponse get(PerformerFullRequest request) {
		apiKeySupplier.supply(request);
		return performerPortType.getPerformerFull(request);
	}

	public PerformerBaseResponse search(PerformerBaseRequest request) {
		apiKeySupplier.supply(request);
		return performerPortType.getPerformerBase(request);
	}

}
