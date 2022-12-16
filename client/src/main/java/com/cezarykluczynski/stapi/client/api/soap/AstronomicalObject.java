package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;

public class AstronomicalObject {

	private final AstronomicalObjectPortType astronomicalObjectPortType;

	public AstronomicalObject(AstronomicalObjectPortType astronomicalObjectPortType) {
		this.astronomicalObjectPortType = astronomicalObjectPortType;
	}

	@Deprecated
	public AstronomicalObjectFullResponse get(AstronomicalObjectFullRequest request) {
		return astronomicalObjectPortType.getAstronomicalObjectFull(request);
	}

	@Deprecated
	public AstronomicalObjectBaseResponse search(AstronomicalObjectBaseRequest request) {
		return astronomicalObjectPortType.getAstronomicalObjectBase(request);
	}

}
