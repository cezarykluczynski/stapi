package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;

public class Species {

	private final SpeciesPortType speciesPortType;

	public Species(SpeciesPortType speciesPortType) {
		this.speciesPortType = speciesPortType;
	}

	@Deprecated
	public SpeciesFullResponse get(SpeciesFullRequest request) {
		return speciesPortType.getSpeciesFull(request);
	}

	@Deprecated
	public SpeciesBaseResponse search(SpeciesBaseRequest request) {
		return speciesPortType.getSpeciesBase(request);
	}

}
