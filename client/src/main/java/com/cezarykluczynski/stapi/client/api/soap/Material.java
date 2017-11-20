package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;

public class Material {

	private final MaterialPortType materialPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Material(MaterialPortType materialPortType, ApiKeySupplier apiKeySupplier) {
		this.materialPortType = materialPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public MaterialFullResponse get(MaterialFullRequest request) {
		apiKeySupplier.supply(request);
		return materialPortType.getMaterialFull(request);
	}

	public MaterialBaseResponse search(MaterialBaseRequest request) {
		apiKeySupplier.supply(request);
		return materialPortType.getMaterialBase(request);
	}

}
