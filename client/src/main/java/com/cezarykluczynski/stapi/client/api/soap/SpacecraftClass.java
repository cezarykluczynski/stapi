package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;

public class SpacecraftClass {

	private final SpacecraftClassPortType spacecraftClassPortType;

	public SpacecraftClass(SpacecraftClassPortType spacecraftClassPortType) {
		this.spacecraftClassPortType = spacecraftClassPortType;
	}

	@Deprecated
	public SpacecraftClassFullResponse get(SpacecraftClassFullRequest request) {
		return spacecraftClassPortType.getSpacecraftClassFull(request);
	}

	@Deprecated
	public SpacecraftClassBaseResponse search(SpacecraftClassBaseRequest request) {
		return spacecraftClassPortType.getSpacecraftClassBase(request);
	}

}
