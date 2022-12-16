package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;

public class Spacecraft {

	private final SpacecraftPortType spacecraftPortType;

	public Spacecraft(SpacecraftPortType spacecraftPortType) {
		this.spacecraftPortType = spacecraftPortType;
	}

	@Deprecated
	public SpacecraftFullResponse get(SpacecraftFullRequest request) {
		return spacecraftPortType.getSpacecraftFull(request);
	}

	@Deprecated
	public SpacecraftBaseResponse search(SpacecraftBaseRequest request) {
		return spacecraftPortType.getSpacecraftBase(request);
	}

}
