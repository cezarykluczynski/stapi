package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;

public class SpacecraftClass {

	private final SpacecraftClassPortType spacecraftClassPortType;

	private final ApiKeySupplier apiKeySupplier;

	public SpacecraftClass(SpacecraftClassPortType spacecraftClassPortType, ApiKeySupplier apiKeySupplier) {
		this.spacecraftClassPortType = spacecraftClassPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SpacecraftClassFullResponse get(SpacecraftClassFullRequest request) {
		apiKeySupplier.supply(request);
		return spacecraftClassPortType.getSpacecraftClassFull(request);
	}

	public SpacecraftClassBaseResponse search(SpacecraftClassBaseRequest request) {
		apiKeySupplier.supply(request);
		return spacecraftClassPortType.getSpacecraftClassBase(request);
	}

}
