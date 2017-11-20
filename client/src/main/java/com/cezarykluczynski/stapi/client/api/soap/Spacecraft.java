package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;

public class Spacecraft {

	private final SpacecraftPortType spacecraftPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Spacecraft(SpacecraftPortType spacecraftPortType, ApiKeySupplier apiKeySupplier) {
		this.spacecraftPortType = spacecraftPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SpacecraftFullResponse get(SpacecraftFullRequest request) {
		apiKeySupplier.supply(request);
		return spacecraftPortType.getSpacecraftFull(request);
	}

	public SpacecraftBaseResponse search(SpacecraftBaseRequest request) {
		apiKeySupplier.supply(request);
		return spacecraftPortType.getSpacecraftBase(request);
	}

}
