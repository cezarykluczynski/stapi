package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType;

public class Element {

	private final ElementPortType elementPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Element(ElementPortType elementPortType, ApiKeySupplier apiKeySupplier) {
		this.elementPortType = elementPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ElementFullResponse get(ElementFullRequest request) {
		apiKeySupplier.supply(request);
		return elementPortType.getElementFull(request);
	}

	public ElementBaseResponse search(ElementBaseRequest request) {
		apiKeySupplier.supply(request);
		return elementPortType.getElementBase(request);
	}

}
