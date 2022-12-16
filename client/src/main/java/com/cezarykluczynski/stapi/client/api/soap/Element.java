package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType;

public class Element {

	private final ElementPortType elementPortType;

	public Element(ElementPortType elementPortType) {
		this.elementPortType = elementPortType;
	}

	@Deprecated
	public ElementFullResponse get(ElementFullRequest request) {
		return elementPortType.getElementFull(request);
	}

	@Deprecated
	public ElementBaseResponse search(ElementBaseRequest request) {
		return elementPortType.getElementBase(request);
	}

}
