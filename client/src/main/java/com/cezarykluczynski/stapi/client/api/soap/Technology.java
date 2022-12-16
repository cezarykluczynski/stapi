package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType;

public class Technology {

	private final TechnologyPortType technologyPortType;

	public Technology(TechnologyPortType technologyPortType) {
		this.technologyPortType = technologyPortType;
	}

	@Deprecated
	public TechnologyFullResponse get(TechnologyFullRequest request) {
		return technologyPortType.getTechnologyFull(request);
	}

	@Deprecated
	public TechnologyBaseResponse search(TechnologyBaseRequest request) {
		return technologyPortType.getTechnologyBase(request);
	}

}

