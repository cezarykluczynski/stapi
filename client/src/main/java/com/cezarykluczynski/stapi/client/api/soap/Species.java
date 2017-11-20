package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;

public class Species {

	private final SpeciesPortType speciesPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Species(SpeciesPortType speciesPortType, ApiKeySupplier apiKeySupplier) {
		this.speciesPortType = speciesPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SpeciesFullResponse get(SpeciesFullRequest request) {
		apiKeySupplier.supply(request);
		return speciesPortType.getSpeciesFull(request);
	}

	public SpeciesBaseResponse search(SpeciesBaseRequest request) {
		apiKeySupplier.supply(request);
		return speciesPortType.getSpeciesBase(request);
	}

}
