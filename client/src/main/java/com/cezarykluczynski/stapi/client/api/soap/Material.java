package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;

public class Material {

	private final MaterialPortType materialPortType;

	public Material(MaterialPortType materialPortType) {
		this.materialPortType = materialPortType;
	}

	@Deprecated
	public MaterialFullResponse get(MaterialFullRequest request) {
		return materialPortType.getMaterialFull(request);
	}

	@Deprecated
	public MaterialBaseResponse search(MaterialBaseRequest request) {
		return materialPortType.getMaterialBase(request);
	}

}
