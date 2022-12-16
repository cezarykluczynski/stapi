package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;

public class Performer {

	private final PerformerPortType performerPortType;

	public Performer(PerformerPortType performerPortType) {
		this.performerPortType = performerPortType;
	}

	@Deprecated
	public PerformerFullResponse get(PerformerFullRequest request) {
		return performerPortType.getPerformerFull(request);
	}

	@Deprecated
	public PerformerBaseResponse search(PerformerBaseRequest request) {
		return performerPortType.getPerformerBase(request);
	}

}
