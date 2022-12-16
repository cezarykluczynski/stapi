package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType;

public class Occupation {

	private final OccupationPortType occupationPortType;

	public Occupation(OccupationPortType occupationPortType) {
		this.occupationPortType = occupationPortType;
	}

	@Deprecated
	public OccupationFullResponse get(OccupationFullRequest request) {
		return occupationPortType.getOccupationFull(request);
	}

	@Deprecated
	public OccupationBaseResponse search(OccupationBaseRequest request) {
		return occupationPortType.getOccupationBase(request);
	}

}
